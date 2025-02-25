package ru.vafeen.whisperoftasks.data.planner

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
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
    private val intent = Intent(context, ReminderReceiver::class.java)
    private fun Reminder.createPendingIntentWithExtras() = PendingIntent.getBroadcast(
        context,
        idOfReminder,
        ReminderReceiver.withExtras(intent, this),
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    private fun scheduleRepeatingJob(reminder: Reminder) {
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + calculateInitialDelay(reminder),
            reminder.repeatDuration.duration.milliSeconds,
            reminder.createPendingIntentWithExtras()
        )
    }

    private fun scheduleOneTimeJob(reminder: Reminder) {
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + calculateInitialDelay(reminder),
            reminder.createPendingIntentWithExtras()
        )
    }

    private fun scheduleJob(reminder: Reminder) {
        when (reminder.repeatDuration) {
            RepeatDuration.NoRepeat -> scheduleOneTimeJob(reminder)
            else -> scheduleRepeatingJob(reminder)
        }
    }

    private fun cancelJob(reminder: Reminder) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                reminder.idOfReminder,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    private fun calculateInitialDelay(reminder: Reminder): Long =
        dtConverter.convertAB(reminder.dt) - dtConverter.convertAB(LocalDateTime.now())


    override fun planWork(vararg reminder: Reminder) {
        reminder.forEach {
            scheduleJob(it)
        }
    }

    override fun planWork(reminders: List<Reminder>) {
        reminders.forEach {
            scheduleJob(it)
        }
    }

    override fun cancelWork(vararg reminder: Reminder) {
        reminder.forEach {
            cancelJob(it)
        }
    }

    override fun cancelWork(reminders: List<Reminder>) {
        reminders.forEach {
            cancelJob(it)
        }
    }

}