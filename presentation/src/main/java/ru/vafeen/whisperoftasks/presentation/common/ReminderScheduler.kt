package ru.vafeen.whisperoftasks.presentation.common

import ru.vafeen.whisperoftasks.domain.domain_models.Reminder


internal interface ReminderScheduler {
    fun setEvent(reminder: Reminder)

    fun unsetEvent(reminder: Reminder)
}