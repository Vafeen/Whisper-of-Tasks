package ru.vafeen.whisperoftasks.data.planner

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import ru.vafeen.whisperoftasks.data.converters.DateTimeConverter
import ru.vafeen.whisperoftasks.domain.domain_models.Reminder
import ru.vafeen.whisperoftasks.domain.duration.RepeatDuration
import ru.vafeen.whisperoftasks.domain.planner.Scheduler
import java.time.LocalDateTime

internal class SchedulerImpl(
    private val context: Context,
    private val dtConverter: DateTimeConverter
) : Scheduler {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    private fun Reminder.createPendingIntentWithExtras() = PendingIntent.getBroadcast(
        context,
        idOfReminder,
        ReminderReceiver.intentWithExtras(context, this),
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    private fun calculateTime(reminder: Reminder): Long = dtConverter.convertAB(reminder.dt)

    private fun scheduleRepeatingJob(reminder: Reminder) {
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calculateTime(reminder),
            reminder.repeatDuration.duration.milliSeconds,
            reminder.createPendingIntentWithExtras()
        )
    }

    private fun scheduleOneTimeJob(reminder: Reminder) {
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calculateTime(reminder),
            reminder.createPendingIntentWithExtras()
        )
    }

    override fun planWork(reminder: Reminder) {
        cancelWork(reminder)
        if ((reminder.dt >= LocalDateTime.now() || reminder.repeatDuration != RepeatDuration.NoRepeat) && !reminder.isDeleted) {
            when (reminder.repeatDuration) {
                RepeatDuration.NoRepeat -> scheduleOneTimeJob(reminder)
                else -> scheduleRepeatingJob(reminder)
            }
        }
    }

    override fun cancelWork(reminder: Reminder) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                reminder.idOfReminder,
                ReminderReceiver.intentWithExtras(context, reminder),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }


}