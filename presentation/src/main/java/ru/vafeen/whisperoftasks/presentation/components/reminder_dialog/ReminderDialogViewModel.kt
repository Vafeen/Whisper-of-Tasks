package ru.vafeen.whisperoftasks.presentation.components.reminder_dialog

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.vafeen.whisperoftasks.domain.database.ReminderRepository
import ru.vafeen.whisperoftasks.domain.domain_models.Reminder
import ru.vafeen.whisperoftasks.domain.utils.getDateStringWithWeekOfDay
import ru.vafeen.whisperoftasks.domain.utils.getTimeDefaultStr
import ru.vafeen.whisperoftasks.presentation.common.EventCreation
import ru.vafeen.whisperoftasks.resources.R
import java.time.LocalDateTime

internal class ReminderDialogViewModel(
    private val reminderRepository: ReminderRepository,
) : ViewModel(), EventCreation {

    fun updateReminder(reminder: Reminder) {
        viewModelScope.launch {
            reminderRepository.insertAllReminders(reminder)
        }
    }

    override fun removeEvent(reminder: Reminder) {
        viewModelScope.launch {
            reminderRepository.deleteAllReminders(reminder)
        }
    }

    override fun updateEvent(reminder: Reminder) {
        viewModelScope.launch {
            reminderRepository.insertAllReminders(reminder)
        }
    }

    fun mainButtonText(context: Context, selectedDateTime: LocalDateTime, reminder: Reminder) =
        if (reminder.isNotificationNeeded) "${
            context.getString(R.string.send)
        } ${selectedDateTime.getDateStringWithWeekOfDay(context = context)} ${
            context.getString(R.string.`in`)
        } ${selectedDateTime.hour.getTimeDefaultStr()}:${selectedDateTime.minute.getTimeDefaultStr()}"
        else context.getString(R.string.add_to_list)

}