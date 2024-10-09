package ru.vafeen.whisperoftasks.noui.planner

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import ru.vafeen.whisperoftasks.noui.duration.RepeatDuration
import ru.vafeen.whisperoftasks.noui.local_database.converters.LocalDateTimeConverters
import ru.vafeen.whisperoftasks.noui.local_database.entity.Reminder
import java.time.LocalDateTime


class Scheduler(
    private val context: Context,
    private val localDateTimeConverters: LocalDateTimeConverters,
) {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private val SKIP_WEEK_IN_DAYS: Long = 7
    private val SKIP_1_DAY_IN_DAYS: Long = 1

    fun Reminder.resultDT(): LocalDateTime? = if (dateOfDone != null) {
        val time = dt.toLocalTime()
        when (repeatDuration) {
            RepeatDuration.EveryDay -> LocalDateTime.of(
                dateOfDone.plusDays(SKIP_1_DAY_IN_DAYS),
                time
            )

            RepeatDuration.EveryWeek -> LocalDateTime.of(
                dateOfDone.plusDays(SKIP_WEEK_IN_DAYS),
                time
            )

            else -> null
        }
    } else dt


    fun planOneTimeWork(reminder: Reminder) {
        val intent = Intent(context, NotificationReminderReceiver::class.java)
        intent.apply {
            putExtra(SchedulerExtra.ID_OF_REMINDER, reminder.idOfReminder)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reminder.idOfReminder,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val resultDt = reminder.resultDT()

        if (resultDt != null) {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                localDateTimeConverters.localDateTimeToLongMilliSeconds(resultDt),
                pendingIntent
            )
        }
    }

    fun planRepeatWork(reminder: Reminder) {
        val intent = Intent(context, NotificationReminderReceiver::class.java)
        intent.apply {
            putExtra(SchedulerExtra.ID_OF_REMINDER, reminder.idOfReminder)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reminder.idOfReminder,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val resultDt = reminder.resultDT()
        if (resultDt != null) {
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                localDateTimeConverters.localDateTimeToLongMilliSeconds(resultDt),
                reminder.repeatDuration.duration.milliSeconds,
                pendingIntent,
            )
        }
    }

    fun cancelWork(reminder: Reminder) {
        val intent = Intent(context, NotificationReminderReceiver::class.java)
        intent.apply {
            putExtra(SchedulerExtra.ID_OF_REMINDER, reminder.idOfReminder)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reminder.idOfReminder,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }
}