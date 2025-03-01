package ru.vafeen.whisperoftasks.data.planner.alarm_manager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import ru.vafeen.whisperoftasks.data.converters.DateTimeConverter
import ru.vafeen.whisperoftasks.data.planner.work_manager.WorkManagerReminderWorker
import ru.vafeen.whisperoftasks.domain.domain_models.Reminder
import ru.vafeen.whisperoftasks.domain.duration.RepeatDuration
import ru.vafeen.whisperoftasks.domain.planner.Scheduler
import java.time.LocalDateTime

internal class AlarmManagerSchedulerImpl(
    private val context: Context,
    private val dateTimeConverter: DateTimeConverter,
    private val alarmManager: AlarmManager,
) : Scheduler {
    override fun planWork(reminder: Reminder) {
        cancelWork(reminder)
        val pendingIntent = AlarmManagerReminderReceiver.pendingIntentWithData(context, reminder)

        if ((reminder.dt >= LocalDateTime.now() || reminder.repeatDuration != RepeatDuration.NoRepeat) && !reminder.isDeleted) {
            when (reminder.repeatDuration) {
                RepeatDuration.NoRepeat -> alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    dateTimeConverter.convertAB(reminder.dt),
                    pendingIntent
                )

                else -> alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    dateTimeConverter.convertAB(reminder.dt),
                    reminder.repeatDuration.duration.toMillis(),
                    pendingIntent,
                )
            }
        }

    }

    override fun cancelWork(reminder: Reminder) {
        alarmManager.cancel(AlarmManagerReminderReceiver.pendingIntentWithData(context, reminder))
    }
}