package ru.vafeen.whisperoftasks.data.planner.work_manager

import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import ru.vafeen.whisperoftasks.domain.domain_models.Reminder
import ru.vafeen.whisperoftasks.domain.duration.RepeatDuration
import ru.vafeen.whisperoftasks.domain.planner.Scheduler
import java.time.LocalDateTime

internal class WorkManagerSchedulerImpl(private val workManager: WorkManager) : Scheduler {
    private fun Reminder.workName() = "workRequest$idOfReminder"
    override fun planWork(reminder: Reminder) {
        cancelWork(reminder)
        if ((reminder.dt >= LocalDateTime.now() || reminder.repeatDuration != RepeatDuration.NoRepeat) && !reminder.isDeleted) {
            when (reminder.repeatDuration) {
                RepeatDuration.NoRepeat -> workManager.enqueueUniqueWork(
                    reminder.workName(),
                    ExistingWorkPolicy.REPLACE,
                    WorkManagerReminderWorker.oneTimeRequestWithData(reminder)
                )

                else -> workManager.enqueueUniquePeriodicWork(
                    reminder.workName(),
                    ExistingPeriodicWorkPolicy.REPLACE,
                    WorkManagerReminderWorker.periodicRequestWithData(reminder)
                )
            }
        }
    }

    override fun cancelWork(reminder: Reminder) {
        workManager.cancelUniqueWork(reminder.workName())
    }

}