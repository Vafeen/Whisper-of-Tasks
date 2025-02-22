package ru.vafeen.whisperoftasks.presentation.components.reminders_screen

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import ru.vafeen.whisperoftasks.domain.database.usecase.DeleteAllRemindersUseCase
import ru.vafeen.whisperoftasks.domain.database.usecase.GetAllAsFlowRemindersUseCase
import ru.vafeen.whisperoftasks.domain.database.usecase.InsertAllRemindersUseCase
import ru.vafeen.whisperoftasks.domain.domain_models.Reminder
import ru.vafeen.whisperoftasks.domain.domain_models.Settings
import ru.vafeen.whisperoftasks.domain.planner.usecase.SetEventUseCase
import ru.vafeen.whisperoftasks.domain.planner.usecase.UnsetEventUseCase
import ru.vafeen.whisperoftasks.domain.shared_preferences.SettingsManager
import ru.vafeen.whisperoftasks.presentation.common.ReminderScheduler

internal class RemindersScreenViewModel(
    private val settingsManager: SettingsManager,
    private val getAllAsFlowRemindersUseCase: GetAllAsFlowRemindersUseCase,
    private val deleteAllRemindersUseCase: DeleteAllRemindersUseCase,
    private val unsetEventUseCase: UnsetEventUseCase,
    private val setEventUseCase: SetEventUseCase,
) : ViewModel(), ReminderScheduler {
    val settings = settingsManager.settingsFlow
    val remindersFlow =
        getAllAsFlowRemindersUseCase.invoke().stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            emptyList()
        )

    val remindersForDeleting = mutableStateMapOf<Int, Reminder>()
    fun saveSettings(saving: (Settings) -> Settings) {
        settingsManager.save(saving = saving)
    }

    fun setReminderAsCandidateForDeleting(reminder: Reminder) {
        remindersForDeleting[reminder.idOfReminder] = reminder
    }

    private fun unsetReminderAsCandidateForDeleting(reminder: Reminder) {
        remindersForDeleting.remove(reminder.idOfReminder)
    }

    private fun isThisReminderIsCandidateForDeleting(reminder: Reminder): Boolean =
        remindersForDeleting.containsKey(reminder.idOfReminder)

    fun clearRemindersForDeleting() {
        remindersForDeleting.clear()
    }

    fun changeStatusForDeleting(reminder: Reminder) =
        if (!isThisReminderIsCandidateForDeleting(reminder))
            setReminderAsCandidateForDeleting(reminder)
        else unsetReminderAsCandidateForDeleting(reminder)


    fun removeEventsForReminderForDeleting() =
        unsetEventUseCase.invoke(remindersForDeleting.values.toList())


    override suspend fun setEvent(reminder: Reminder) = setEventUseCase.invoke(reminder)


    override suspend fun unsetEvent(reminder: Reminder) = unsetEventUseCase.invoke(reminder)

    suspend fun unsetEventsAndRemoveRemindersForRemoving() {
        remindersForDeleting.values.toList().let {
            unsetEventUseCase.invoke(it)
            deleteAllRemindersUseCase.invoke(reminders = it)
        }
        remindersForDeleting.clear()
    }
}