package ru.vafeen.reminder.noui

import ru.vafeen.reminder.noui.local_database.DatabaseRepository
import ru.vafeen.reminder.noui.local_database.entity.Reminder
import ru.vafeen.reminder.noui.time_mananger.Scheduler

class EventCreator(
    private val scheduler: Scheduler,
    private val databaseRepository: DatabaseRepository
) {
    suspend fun addEvent(reminder: Reminder) {
        if (reminder.repeatDuration.duration.seconds != 0L)
            scheduler.planRepeatWork(reminder)
        else scheduler.planOneTimeWork(reminder)
        databaseRepository.insertReminder(reminder)
    }

    suspend fun removeEvent(reminder: Reminder) {
        scheduler.cancelWork(reminder)
        databaseRepository.removeReminder(reminder)
    }

    suspend fun updateEvent(reminder: Reminder) {
        scheduler.cancelWork(reminder)
        addEvent(reminder)
        databaseRepository.insertReminder(reminder)
    }

}