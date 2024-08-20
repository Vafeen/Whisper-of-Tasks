package ru.vafeen.reminder.noui.work_mananger

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import ru.vafeen.reminder.noui.notification.NotificationService
import javax.inject.Inject

class MyWorkerFactory @Inject constructor(
    private val notificationService: NotificationService
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker = MyWorker(
        context = appContext,
        workerParams = workerParameters,
        notificationService = notificationService
    )
}