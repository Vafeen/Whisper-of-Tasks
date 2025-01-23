package ru.vafeen.whisperoftasks.presentation.common

import ru.vafeen.whisperoftasks.domain.domain_models.Reminder


internal interface ReminderScheduler {
    fun setEvent(vararg reminder: Reminder)

    fun unsetEvent(vararg reminder: Reminder)
}