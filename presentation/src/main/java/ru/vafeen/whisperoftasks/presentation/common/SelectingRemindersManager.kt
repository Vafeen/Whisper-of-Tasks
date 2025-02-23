package ru.vafeen.whisperoftasks.presentation.common

import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.platform.LocalGraphicsContext
import ru.vafeen.whisperoftasks.domain.domain_models.Reminder

interface SelectingRemindersManager {
    val selectedReminders: SnapshotStateMap<Int, Reminder>
    fun changeStatusForDeleting(reminder: Reminder) {
        if (!selectedReminders.containsKey(reminder.idOfReminder))
            selectedReminders[reminder.idOfReminder] = reminder
        else selectedReminders.remove(reminder.idOfReminder)
    }

    fun clearSelectedReminders() = selectedReminders.clear()
}