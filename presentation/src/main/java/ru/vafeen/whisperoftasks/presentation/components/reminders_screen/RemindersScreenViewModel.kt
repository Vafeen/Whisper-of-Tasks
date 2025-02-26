package ru.vafeen.whisperoftasks.presentation.components.reminders_screen

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.map
import ru.vafeen.whisperoftasks.domain.database.usecase.GetAllAsFlowRemindersUseCase
import ru.vafeen.whisperoftasks.domain.database.usecase.MoveToTrashBinReminderUseCase
import ru.vafeen.whisperoftasks.domain.domain_models.Reminder
import ru.vafeen.whisperoftasks.domain.domain_models.Settings
import ru.vafeen.whisperoftasks.domain.notification.usecase.ShowNotificationTaskUseCase
import ru.vafeen.whisperoftasks.domain.planner.usecase.PlanWorkUseCase
import ru.vafeen.whisperoftasks.domain.planner.usecase.CancelWorkUseCase
import ru.vafeen.whisperoftasks.domain.shared_preferences.SettingsManager
import ru.vafeen.whisperoftasks.domain.utils.launchIO
import ru.vafeen.whisperoftasks.presentation.common.ReminderScheduler
import ru.vafeen.whisperoftasks.presentation.common.SelectingRemindersManager

internal class RemindersScreenViewModel(
    private val settingsManager: SettingsManager,
    private val getAllAsFlowRemindersUseCase: GetAllAsFlowRemindersUseCase,
    private val moveToTrashBinReminderUseCase: MoveToTrashBinReminderUseCase,
    private val cancelWorkUseCase: CancelWorkUseCase,
    private val planWorkUseCase: PlanWorkUseCase,
    private val showNotificationTaskUseCase: ShowNotificationTaskUseCase,
) : ViewModel(), ReminderScheduler, SelectingRemindersManager {
    val settings = settingsManager.settingsFlow
    val remindersFlow =
        getAllAsFlowRemindersUseCase.invoke().map {
            it.filter { r -> !r.isDeleted }
        }

    override val selectedReminders: SnapshotStateMap<Int, Reminder> = mutableStateMapOf()
    fun saveSettings(saving: (Settings) -> Settings) {
        settingsManager.save(saving = saving)
    }


    override fun setEvent(reminder: Reminder) = planWorkUseCase.invoke(reminder)

    override fun unsetEvent(reminder: Reminder) = cancelWorkUseCase.invoke(reminder)

    fun moveToTrashSelectedReminders() {
        viewModelScope.launchIO {
            selectedReminders.values.forEach {
                cancelWorkUseCase.invoke(it)
                moveToTrashBinReminderUseCase.invoke(it)
            }
            clearSelectedReminders()
        }
    }

    fun showNotification(reminder: Reminder) =
        showNotificationTaskUseCase.invoke(reminder.title, reminder.text)
}