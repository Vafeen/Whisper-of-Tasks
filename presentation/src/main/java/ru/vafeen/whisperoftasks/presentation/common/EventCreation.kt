package ru.vafeen.whisperoftasks.presentation.common

import ru.vafeen.whisperoftasks.domain.domain_models.Reminder


internal interface EventCreation {
    fun removeEvent(reminder: Reminder)

    fun updateEvent(reminder: Reminder)
}