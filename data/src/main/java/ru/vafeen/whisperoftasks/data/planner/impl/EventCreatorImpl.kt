package ru.vafeen.whisperoftasks.data.planner.impl

import android.content.Intent
import org.koin.java.KoinJavaComponent.inject
import ru.vafeen.whisperoftasks.domain.local_database.ReminderRepository
import ru.vafeen.whisperoftasks.domain.models.Reminder
import ru.vafeen.whisperoftasks.domain.planner.EventCreator
import ru.vafeen.whisperoftasks.domain.planner.Scheduler

internal class EventCreatorImpl(
    private val reminderRepository: ReminderRepository,
) : EventCreator {
    private val scheduler: Scheduler by inject(clazz = Scheduler::class.java)
    override suspend fun planeEvent(reminder: Reminder, intent: Intent) {
        scheduler.cancelWork(reminder = reminder, intent = intent)
        if (reminder.isNotificationNeeded)
            if (reminder.repeatDuration.duration.milliSeconds != 0L)
                scheduler.planRepeatWork(reminder = reminder, intent = intent)
            else scheduler.planOneTimeWork(reminder = reminder, intent = intent)
        reminderRepository.insertAllReminders(reminder)
    }

    override suspend fun removeEvent(reminder: Reminder, intent: Intent) {
        scheduler.cancelWork(reminder = reminder, intent = intent)
        reminderRepository.deleteAllReminders(reminder)
    }
}