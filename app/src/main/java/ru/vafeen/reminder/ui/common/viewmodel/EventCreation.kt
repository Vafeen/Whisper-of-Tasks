package ru.vafeen.reminder.ui.common.viewmodel

import ru.vafeen.reminder.noui.EventCreator
import ru.vafeen.reminder.noui.local_database.entity.Reminder

interface EventCreation {
    val eventCreator: EventCreator

    fun removeEvent(reminder: Reminder)

    fun updateEvent(reminder: Reminder)
}