package ru.vafeen.whisperoftasks.presentation.components.main_screen

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.map
import ru.vafeen.whisperoftasks.domain.database.usecase.GetAllAsFlowRemindersUseCase
import ru.vafeen.whisperoftasks.domain.database.usecase.InsertAllRemindersUseCase
import ru.vafeen.whisperoftasks.domain.database.usecase.MoveToTrashBinReminderUseCase
import ru.vafeen.whisperoftasks.domain.domain_models.Reminder
import ru.vafeen.whisperoftasks.domain.domain_models.Settings
import ru.vafeen.whisperoftasks.domain.notification.usecase.ShowNotificationTaskUseCase
import ru.vafeen.whisperoftasks.domain.planner.usecase.PlanWorkUseCase
import ru.vafeen.whisperoftasks.domain.planner.usecase.CancelWorkUseCase
import ru.vafeen.whisperoftasks.domain.shared_preferences.SettingsManager
import ru.vafeen.whisperoftasks.domain.utils.launchIO
import ru.vafeen.whisperoftasks.presentation.common.SelectingRemindersManager
import java.time.LocalDate


internal class MainScreenViewModel(
    private val getAllAsFlowRemindersUseCase: GetAllAsFlowRemindersUseCase,
    private val moveToTrashBinReminderUseCase: MoveToTrashBinReminderUseCase,
    private val insertAllRemindersUseCase: InsertAllRemindersUseCase,
    private val settingsManager: SettingsManager,
    private val cancelWorkUseCase: CancelWorkUseCase,
    private val planWorkUseCase: PlanWorkUseCase,
    private val showNotificationTaskUseCase: ShowNotificationTaskUseCase,
) : ViewModel(), SelectingRemindersManager {
    val settings = settingsManager.settingsFlow
    val todayDate: LocalDate = LocalDate.now()
    val remindersFlow = getAllAsFlowRemindersUseCase.invoke().map {
        it.filter { r -> !r.isDeleted }
    }
    override val selectedReminders: SnapshotStateMap<Int, Reminder> = mutableStateMapOf()
    fun moveToTrashSelectedReminders() {
        viewModelScope.launchIO {
            selectedReminders.values.forEach {
                cancelWorkUseCase.invoke(it)
                moveToTrashBinReminderUseCase.invoke(it)
            }
            clearSelectedReminders()
        }
    }
    fun saveSettings(saving: (Settings) -> Settings) {
        settingsManager.save(saving = saving)
    }

    fun setEvent(reminder: Reminder) {
        viewModelScope.launchIO {
            insertAllRemindersUseCase.invoke(reminder)
            planWorkUseCase.invoke(reminder)
        }
    }
    fun showNotification(reminder: Reminder) =
        showNotificationTaskUseCase.invoke(reminder.title, reminder.text)
}