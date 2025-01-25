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


    private fun scheduleRepeatingJob(reminder: Reminder) {
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reminder.idOfReminder,
            intent.also {
                it.putExtra(SchedulerExtra.ID_OF_REMINDER, reminder.idOfReminder)
            },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + calculateInitialDelay(reminder),
            reminder.repeatDuration.duration.milliSeconds,
            pendingIntent
        )
    }

    private fun scheduleOneTimeJob(reminder: Reminder) {
        Log.d("sch", "scheduled do")
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + calculateInitialDelay(reminder),
            PendingIntent.getBroadcast(
                context,
                reminder.idOfReminder,
                intent.also {
                    it.putExtra(SchedulerExtra.ID_OF_REMINDER, reminder.idOfReminder)
                },
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
        Log.d("sch", "scheduledafter")
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

    private fun calculateInitialDelay(reminder: Reminder): Long {
        val now = dtConverter.convertAB(LocalDateTime.now())
        val reminderTime = dtConverter.convertAB(reminder.dt)
        return if (reminderTime > now) reminderTime - now else 0L
    }

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