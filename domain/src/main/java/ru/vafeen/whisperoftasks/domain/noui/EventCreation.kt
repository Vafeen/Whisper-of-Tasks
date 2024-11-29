package ru.vafeen.whisperoftasks.domain.noui

import ru.vafeen.whisperoftasks.data.local_database.entity.Reminder

interface EventCreation {
    fun removeEvent(reminder: Reminder)

    fun updateEvent(reminder: Reminder)
}