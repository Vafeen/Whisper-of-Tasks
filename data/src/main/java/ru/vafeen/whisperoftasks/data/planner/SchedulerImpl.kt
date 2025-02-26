package ru.vafeen.whisperoftasks.data.planner

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import ru.vafeen.whisperoftasks.data.converters.DateTimeConverter
import ru.vafeen.whisperoftasks.domain.domain_models.Reminder
import ru.vafeen.whisperoftasks.domain.duration.RepeatDuration
import ru.vafeen.whisperoftasks.domain.planner.Scheduler

internal class SchedulerImpl(
    private val context: Context,
    private val dtConverter: DateTimeConverter
) : Scheduler {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private val intent = Intent(context, ReminderReceiver::class.java)
    private fun Reminder.createPendingIntentWithExtras() = PendingIntent.getBroadcast(
        context,
        idOfReminder,
        ReminderReceiver.withExtras(intent, this),
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
        when (reminder.repeatDuration) {
            RepeatDuration.NoRepeat -> scheduleOneTimeJob(reminder)
            else -> scheduleRepeatingJob(reminder)
        }
    }

    override fun cancelWork(reminder: Reminder) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                reminder.idOfReminder,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }


}