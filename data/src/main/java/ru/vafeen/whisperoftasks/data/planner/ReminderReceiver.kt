package ru.vafeen.whisperoftasks.data.planner

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import org.koin.core.component.KoinComponent

class ReminderReceiver : BroadcastReceiver(), KoinComponent {
    override fun onReceive(context: Context, intent: Intent) {
        intent.getIntExtra(SchedulerExtra.ID_OF_REMINDER, -1).let { reminderId ->
            if (reminderId != -1) {
                // Запускаем Worker с данными
                getKoin().get<WorkManager>().enqueue(
                    OneTimeWorkRequestBuilder<ReminderWorker>()
                        .setInputData(
                            Data.Builder()
                                .putInt(SchedulerExtra.ID_OF_REMINDER, reminderId)
                                .build()
                        )
                        .build()
                )
            }
        }
    }
}