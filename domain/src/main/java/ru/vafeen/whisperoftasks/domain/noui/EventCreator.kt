package ru.vafeen.whisperoftasks.domain.noui

import android.content.Intent
import org.koin.java.KoinJavaComponent.inject
import ru.vafeen.whisperoftasks.data.local_database.DatabaseRepository
import ru.vafeen.whisperoftasks.data.local_database.entity.Reminder
import ru.vafeen.whisperoftasks.domain.noui.planner.Scheduler

class EventCreator(
    private val databaseRepository: DatabaseRepository,
) {
    private val scheduler: Scheduler by inject(clazz = Scheduler::class.java)
    suspend fun planeEvent(reminder: Reminder, intent: Intent) {
        scheduler.cancelWork(reminder = reminder, intent = intent)
        if (reminder.isNotificationNeeded)
            if (reminder.repeatDuration.duration.milliSeconds != 0L)
                scheduler.planRepeatWork(reminder = reminder, intent = intent)
            else scheduler.planOneTimeWork(reminder = reminder, intent = intent)
        databaseRepository.insertAllReminders(reminder)
    }

    suspend fun removeEvent(reminder: Reminder, intent: Intent) {
        scheduler.cancelWork(reminder = reminder, intent = intent)
        databaseRepository.deleteAllReminders(reminder)
    }
}