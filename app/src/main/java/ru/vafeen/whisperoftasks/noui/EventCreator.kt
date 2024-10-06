package ru.vafeen.whisperoftasks.noui

import ru.vafeen.whisperoftasks.noui.local_database.DatabaseRepository
import ru.vafeen.whisperoftasks.noui.local_database.entity.Reminder
import ru.vafeen.whisperoftasks.noui.planner.Scheduler

class EventCreator(
    private val scheduler: Scheduler,
    private val databaseRepository: DatabaseRepository,
) {
    suspend fun planeEvent(reminder: Reminder) {
        scheduler.cancelWork(reminder)
        if (reminder.isNotificationNeeded)
            if (reminder.repeatDuration.duration.milliSeconds != 0L)
                scheduler.planRepeatWork(reminder)
            else scheduler.planOneTimeWork(reminder)
        databaseRepository.insertAllReminders(reminder)
    }

    suspend fun removeEvent(reminder: Reminder) {
        scheduler.cancelWork(reminder)
        databaseRepository.deleteAllReminders(reminder)
    }
}