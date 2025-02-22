package ru.vafeen.whisperoftasks.presentation.components.reminder_dialog

import android.content.Context
import androidx.lifecycle.ViewModel
import ru.vafeen.whisperoftasks.domain.database.usecase.DeleteAllRemindersUseCase
import ru.vafeen.whisperoftasks.domain.database.usecase.InsertAllRemindersUseCase
import ru.vafeen.whisperoftasks.domain.domain_models.Reminder
import ru.vafeen.whisperoftasks.domain.planner.usecase.SetEventUseCase
import ru.vafeen.whisperoftasks.domain.planner.usecase.UnsetEventUseCase
import ru.vafeen.whisperoftasks.domain.shared_preferences.SettingsManager
import ru.vafeen.whisperoftasks.domain.utils.getDateStringWithWeekOfDay
import ru.vafeen.whisperoftasks.domain.utils.getTimeDefaultStr
import ru.vafeen.whisperoftasks.presentation.common.ReminderScheduler
import ru.vafeen.whisperoftasks.resources.R
import java.time.LocalDateTime

internal class ReminderDialogViewModel(
    private val deleteAllRemindersUseCase: DeleteAllRemindersUseCase,
    private val insertAllRemindersUseCase: InsertAllRemindersUseCase,
    private val unsetEventUseCase: UnsetEventUseCase,
    private val setEventUseCase: SetEventUseCase,
    private val settingsManager: SettingsManager
) : ViewModel(), ReminderScheduler {
    val settings = settingsManager.settingsFlow
    fun mainButtonText(context: Context, selectedDateTime: LocalDateTime, reminder: Reminder) =
        if (reminder.isNotificationNeeded) "${
            context.getString(R.string.send)
        } ${selectedDateTime.getDateStringWithWeekOfDay(context = context)} ${
            context.getString(R.string.`in`)
        } ${selectedDateTime.hour.getTimeDefaultStr()}:${selectedDateTime.minute.getTimeDefaultStr()}"
        else context.getString(R.string.add_to_list)

    override suspend fun setEvent(reminder: Reminder) {
        insertAllRemindersUseCase.invoke(reminder)
        setEventUseCase.invoke(listOf(reminder))
    }

    override suspend fun unsetEvent(reminder: Reminder) {
        deleteAllRemindersUseCase.invoke(reminder)
        unsetEventUseCase.invoke(listOf(reminder))
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
            setEventUseCase.invoke(listOf(newReminder))
        }
        insertAllRemindersUseCase.invoke(newReminder)
    }
}