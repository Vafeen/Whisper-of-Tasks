package ru.vafeen.reminder.noui.work_mananger

import android.annotation.SuppressLint
import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.vafeen.reminder.noui.local_database.entity.Reminder
import ru.vafeen.reminder.utils.toLongSeconds
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@SuppressLint("RestrictedApi")
class Scheduler @Inject constructor(@ApplicationContext private val context: Context) {
    private val workManager = WorkManager.getInstance(context)

    fun planOneTimeWork(reminder: Reminder) {
        val myWorkRequest = OneTimeWorkRequestBuilder<MyWorker>().setScheduleRequestedAt(
            scheduleRequestedAt = reminder.dt.toLongSeconds(), timeUnit = TimeUnit.SECONDS
        ).setInputData(reminder.getInputData())
            .setInitialDelay(
                reminder.dt.toLongSeconds() - LocalDateTime.now().toLongSeconds(),
                TimeUnit.SECONDS
            )
            .setId(reminder.UUID)
            .build()
        workManager.enqueue(myWorkRequest)
    }

    fun planRepeatWork(reminder: Reminder) {
        val seconds = reminder.repeatDuration.duration.seconds
        val myWorkRequest = PeriodicWorkRequestBuilder<MyWorker>(
            seconds,
            TimeUnit.SECONDS,
            1,
            TimeUnit.MINUTES
        )
            .setInitialDelay(
                reminder.dt.toLongSeconds() - LocalDateTime.now().toLongSeconds(),
                TimeUnit.SECONDS
            )
            .setId(reminder.UUID)
            .setInputData(reminder.getInputData())
            .setScheduleRequestedAt(
                scheduleRequestedAt = reminder.dt.toLongSeconds(),
                timeUnit = TimeUnit.SECONDS
            )
            .build()
        workManager.enqueue(myWorkRequest)
    }

    fun cancelWork(reminder: Reminder) {
        workManager.cancelWorkById(reminder.UUID)
    }

    private fun Reminder.getInputData(): Data = Data.Builder().also {
        it.putString(DataKeys.TITLE, title)
        it.putString(DataKeys.TEXT, text)
    }.build()
}