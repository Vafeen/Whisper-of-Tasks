package ru.vafeen.whisperoftasks.presentation.common

import ru.vafeen.whisperoftasks.domain.domain_models.Reminder


internal interface ReminderScheduler {
    suspend fun setEvent(reminder: Reminder)

    suspend fun unsetEvent(reminder: Reminder)
}