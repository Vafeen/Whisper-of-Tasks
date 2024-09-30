package ru.vafeen.reminder.noui

import ru.vafeen.reminder.noui.local_database.DatabaseRepository
import ru.vafeen.reminder.noui.local_database.entity.Reminder
import ru.vafeen.reminder.noui.planner.Scheduler

class EventCreator(
    private val scheduler: Scheduler,
    private val databaseRepository: DatabaseRepository
) {
    suspend fun addEvent(reminder: Reminder) {
        if (reminder.repeatDuration.duration.milliSeconds != 0L)
            scheduler.planRepeatWork(reminder)
        else scheduler.planOneTimeWork(reminder)
        databaseRepository.insertAllReminders(reminder)
    }

    suspend fun removeEvent(reminder: Reminder) {
        scheduler.cancelWork(reminder)
        databaseRepository.deleteAllReminders(reminder)
    }

    suspend fun updateEvent(reminder: Reminder) {
        scheduler.cancelWork(reminder)
        addEvent(reminder)
        databaseRepository.insertAllReminders(reminder)
    }

}