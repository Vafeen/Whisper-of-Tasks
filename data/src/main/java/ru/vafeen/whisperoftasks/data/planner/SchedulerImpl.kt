package ru.vafeen.whisperoftasks.data.planner

import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import ru.vafeen.whisperoftasks.domain.domain_models.Reminder
import ru.vafeen.whisperoftasks.domain.duration.RepeatDuration
import ru.vafeen.whisperoftasks.domain.planner.Scheduler
import java.time.LocalDateTime

internal class SchedulerImpl(private val workManager: WorkManager) : Scheduler {
    private fun Reminder.workName() = "workRequest$idOfReminder"
    override fun planWork(reminder: Reminder) {
        cancelWork(reminder)
        if ((reminder.dt >= LocalDateTime.now() || reminder.repeatDuration != RepeatDuration.NoRepeat) && !reminder.isDeleted) {
            when (reminder.repeatDuration) {
                RepeatDuration.NoRepeat -> workManager.enqueueUniqueWork(
                    reminder.workName(),
                    ExistingWorkPolicy.REPLACE,
                    ReminderWorker.oneTimeRequestWithData(reminder)
                )

                else -> workManager.enqueueUniquePeriodicWork(
                    reminder.workName(),
                    ExistingPeriodicWorkPolicy.REPLACE,
                    ReminderWorker.periodicRequestWithData(reminder)
                )
            }
        }
    }

    override fun cancelWork(reminder: Reminder) {
        workManager.cancelUniqueWork(reminder.workName())
    }

}