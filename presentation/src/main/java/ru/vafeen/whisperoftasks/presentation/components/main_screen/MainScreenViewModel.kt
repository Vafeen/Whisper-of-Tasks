package ru.vafeen.whisperoftasks.presentation.components.main_screen

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import ru.vafeen.whisperoftasks.domain.database.usecase.DeleteAllRemindersUseCase
import ru.vafeen.whisperoftasks.domain.database.usecase.GetAllAsFlowRemindersUseCase
import ru.vafeen.whisperoftasks.domain.database.usecase.InsertAllRemindersUseCase
import ru.vafeen.whisperoftasks.domain.domain_models.Reminder
import ru.vafeen.whisperoftasks.domain.domain_models.Settings
import ru.vafeen.whisperoftasks.domain.planner.usecase.SetEventUseCase
import ru.vafeen.whisperoftasks.domain.planner.usecase.UnsetEventUseCase
import ru.vafeen.whisperoftasks.domain.shared_preferences.SettingsManager
import ru.vafeen.whisperoftasks.presentation.common.ReminderScheduler
import java.time.LocalDate


internal class MainScreenViewModel(
    private val getAllAsFlowRemindersUseCase: GetAllAsFlowRemindersUseCase,
    private val deleteAllRemindersUseCase: DeleteAllRemindersUseCase,
    private val insertAllRemindersUseCase: InsertAllRemindersUseCase,
    private val settingsManager: SettingsManager,
    private val unsetEventUseCase: UnsetEventUseCase,
    private val setEventUseCase: SetEventUseCase,
) : ViewModel(), ReminderScheduler {
    val settings = settingsManager.settingsFlow
    val todayDate: LocalDate = LocalDate.now()
    val remindersFlow = getAllAsFlowRemindersUseCase.invoke()
    val remindersForDeleting = mutableStateMapOf<Int, Reminder>()

    fun setReminderAsCandidateForDeleting(reminder: Reminder) {
        remindersForDeleting[reminder.idOfReminder] = reminder
    }

    private fun unsetReminderAsCandidateForDeleting(reminder: Reminder) {
        remindersForDeleting.remove(reminder.idOfReminder)
    }

    private fun isThisReminderIsCandidateForDeleting(reminder: Reminder): Boolean =
        remindersForDeleting.containsKey(reminder.idOfReminder)

    fun changeStatusForDeleting(reminder: Reminder) =
        if (!isThisReminderIsCandidateForDeleting(reminder))
            setReminderAsCandidateForDeleting(reminder)
        else unsetReminderAsCandidateForDeleting(reminder)

    fun saveSettings(saving: (Settings) -> Settings) {
        settingsManager.save(saving = saving)
    }

    fun clearRemindersForDeleting() {
        remindersForDeleting.clear()
    }

    suspend fun unsetEventsAndRemoveRemindersForRemoving() {
        remindersForDeleting.values.toList().let {
            unsetEventUseCase.invoke(it)
            deleteAllRemindersUseCase.invoke(reminders = it)
        }
        remindersForDeleting.clear()
    }

    override suspend fun setEvent(reminder: Reminder) {
        insertAllRemindersUseCase.invoke(reminder)
        setEventUseCase.invoke(reminder)
    }

    override suspend fun unsetEvent(reminder: Reminder) {
        deleteAllRemindersUseCase.invoke(reminder)
        unsetEventUseCase.invoke(reminder)
    }
}