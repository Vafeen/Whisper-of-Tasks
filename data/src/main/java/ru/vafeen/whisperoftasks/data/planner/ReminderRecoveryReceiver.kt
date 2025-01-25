package ru.vafeen.whisperoftasks.data.planner

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import org.koin.core.component.KoinComponent

class ReminderRecoveryReceiver : BroadcastReceiver(), KoinComponent {
    private val workManager: WorkManager = getKoin().get()
    override fun onReceive(context: Context?, intent: Intent?) {
        workManager.enqueue(OneTimeWorkRequestBuilder<ReminderRecoveryWorker>().build())
    }
}
