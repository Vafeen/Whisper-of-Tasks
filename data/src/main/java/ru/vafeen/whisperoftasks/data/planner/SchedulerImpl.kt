package ru.vafeen.whisperoftasks.data.planner

import android.app.AlarmManager
import android.content.Context
import ru.vafeen.whisperoftasks.domain.domain_models.Reminder
import ru.vafeen.whisperoftasks.domain.duration.RepeatDuration
import ru.vafeen.whisperoftasks.domain.planner.Scheduler
import java.time.LocalDateTime


internal class SchedulerImpl(
    private val context: Context,
//    private val localDateTimeConverters: LocalDateTimeConverters,
) : Scheduler {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private val SKIP_WEEK_IN_DAYS: Long = 7
    private val SKIP_1_DAY_IN_DAYS: Long = 1

    private fun Reminder.resultDT(): LocalDateTime? = if (dateOfDone != null) {
        val time = dt.toLocalTime()
        when (repeatDuration) {
            RepeatDuration.EveryDay -> LocalDateTime.of(
                dateOfDone?.plusDays(SKIP_1_DAY_IN_DAYS),
                time
            )

            RepeatDuration.EveryWeek -> LocalDateTime.of(
                dateOfDone?.plusDays(SKIP_WEEK_IN_DAYS),
                time
            )

            else -> null
        }
    } else dt


    override fun planOneTimeWork(reminder: Reminder) {}
//    {
//        intent.apply {
//            putExtra(SchedulerExtra.ID_OF_REMINDER, reminder.idOfReminder)
//        }
//        val pendingIntent = PendingIntent.getBroadcast(
//            context,
//            reminder.idOfReminder,
//            intent,
//            PendingIntent.FLAG_IMMUTABLE
//        )
//        val resultDt = reminder.resultDT()
//
//        if (resultDt != null) {
//            alarmManager.setExact(
//                AlarmManager.RTC_WAKEUP,
//                localDateTimeConverters.localDateTimeToLongMilliSeconds(resultDt),
//                pendingIntent
//            )
//        }
//    }

    override fun planRepeatWork(reminder: Reminder) {}

    //    {
//        intent.apply {
//            putExtra(SchedulerExtra.ID_OF_REMINDER, reminder.idOfReminder)
//        }
//        val pendingIntent = PendingIntent.getBroadcast(
//            context,
//            reminder.idOfReminder,
//            intent,
//            PendingIntent.FLAG_IMMUTABLE
//        )
//        val resultDt = reminder.resultDT()
//        if (resultDt != null) {
//            alarmManager.setRepeating(
//                AlarmManager.RTC_WAKEUP,
//                localDateTimeConverters.localDateTimeToLongMilliSeconds(resultDt),
//                reminder.repeatDuration.duration.milliSeconds,
//                pendingIntent,
//            )
//        }
//    }
    override fun cancelWork(reminder: Reminder) {}
//    {
//        intent.apply {
//            putExtra(SchedulerExtra.ID_OF_REMINDER, reminder.idOfReminder)
//        }
//        val pendingIntent = PendingIntent.getBroadcast(
//            context,
//            reminder.idOfReminder,
//            intent,
//            PendingIntent.FLAG_IMMUTABLE
//        )
//        alarmManager.cancel(pendingIntent)
//    }
}