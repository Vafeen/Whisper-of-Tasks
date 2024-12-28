package ru.vafeen.whisperoftasks.domain.planner

import ru.vafeen.whisperoftasks.domain.models.Reminder

interface EventCreation {
    fun removeEvent(reminder: Reminder)

    fun updateEvent(reminder: Reminder)
}