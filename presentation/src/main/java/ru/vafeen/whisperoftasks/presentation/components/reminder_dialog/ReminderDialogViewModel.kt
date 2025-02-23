package ru.vafeen.whisperoftasks.presentation.components.reminder_dialog

import android.content.Context
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.vafeen.whisperoftasks.domain.database.usecase.InsertAllRemindersUseCase
import ru.vafeen.whisperoftasks.domain.domain_models.Reminder
import ru.vafeen.whisperoftasks.domain.planner.usecase.PlanWorkUseCase
import ru.vafeen.whisperoftasks.domain.planner.usecase.CancelWorkUseCase
import ru.vafeen.whisperoftasks.domain.shared_preferences.SettingsManager
import ru.vafeen.whisperoftasks.domain.utils.getDateStringWithWeekOfDay
import ru.vafeen.whisperoftasks.domain.utils.getTimeDefaultStr
import ru.vafeen.whisperoftasks.domain.utils.launchIO
import ru.vafeen.whisperoftasks.presentation.common.SelectingRemindersManager
import ru.vafeen.whisperoftasks.resources.R
import java.time.LocalDateTime

internal class ReminderDialogViewModel(
    private val insertAllRemindersUseCase: InsertAllRemindersUseCase,
    private val cancelWorkUseCase: CancelWorkUseCase,
    private val planWorkUseCase: PlanWorkUseCase,
    private val settingsManager: SettingsManager
) : ViewModel(), SelectingRemindersManager {
    val settings = settingsManager.settingsFlow
    override val selectedReminders: SnapshotStateMap<Int, Reminder> = mutableStateMapOf()
    fun mainButtonText(context: Context, selectedDateTime: LocalDateTime, reminder: Reminder) =
        if (reminder.isNotificationNeeded) "${
            context.getString(R.string.send)
        } ${selectedDateTime.getDateStringWithWeekOfDay(context = context)} ${
            context.getString(R.string.`in`)
        } ${selectedDateTime.hour.getTimeDefaultStr()}:${selectedDateTime.minute.getTimeDefaultStr()}"
        else context.getString(R.string.add_to_list)

    fun setEvent(reminder: Reminder) {
        viewModelScope.launchIO {
            insertAllRemindersUseCase.invoke(reminder)
            planWorkUseCase.invoke(listOf(reminder))
        }
    }

    suspend fun updateReminderAndEventDependsOnChangedFields(
        lastReminder: Reminder,
        newReminder: Reminder
    ) {
        if (
            (lastReminder.dt != newReminder.dt ||
                    lastReminder.isNotificationNeeded != newReminder.isNotificationNeeded ||
                    lastReminder.repeatDuration != newReminder.repeatDuration) && newReminder.isNotificationNeeded
        ) {
            planWorkUseCase.invoke(listOf(newReminder))
        }
        insertAllRemindersUseCase.invoke(newReminder)
    }
}