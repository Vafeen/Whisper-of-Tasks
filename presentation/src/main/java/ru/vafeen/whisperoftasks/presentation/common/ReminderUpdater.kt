package ru.vafeen.whisperoftasks.presentation.common

import ru.vafeen.whisperoftasks.domain.domain_models.Reminder

internal interface ReminderUpdater {
    suspend fun insertReminder(vararg reminder: Reminder)
    suspend fun removeReminder(vararg reminder: Reminder)
}