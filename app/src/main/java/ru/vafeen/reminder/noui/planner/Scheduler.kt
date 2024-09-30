package ru.vafeen.reminder.noui.planner

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import ru.vafeen.reminder.noui.local_database.converters.DTConverters
import ru.vafeen.reminder.noui.local_database.entity.Reminder


class Scheduler(
    private val context: Context,
    private val dtConverters: DTConverters
) {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager


    fun planOneTimeWork(reminder: Reminder) {
        val intent = Intent(context, NotificationAboutLessonReceiver::class.java)
        intent.apply {
            putExtra(SchedulerExtra.ID_OF_REMINDER, reminder.idOfReminder)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reminder.idOfReminder,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            dtConverters.localDateTimeToLongMilliSeconds(reminder.dt),
            pendingIntent
        )
    }

    fun planRepeatWork(reminder: Reminder) {

    }

    fun cancelWork(reminder: Reminder) {

    }

}