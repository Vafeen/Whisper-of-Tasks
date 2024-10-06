package ru.vafeen.whisperoftasks.ui.common.components

import ru.vafeen.whisperoftasks.noui.EventCreator
import ru.vafeen.whisperoftasks.noui.local_database.entity.Reminder

interface EventCreation {
    val eventCreator: EventCreator

    fun removeEvent(reminder: Reminder)

    fun updateEvent(reminder: Reminder)
}