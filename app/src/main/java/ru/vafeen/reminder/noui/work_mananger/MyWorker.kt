package ru.vafeen.reminder.noui.work_mananger

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import ru.vafeen.reminder.noui.notification.NotificationService


class MyWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val notificationService: NotificationService
) : Worker(context, workerParams) {
    override fun doWork(): Result {
        val title = inputData.getString(DataKeys.TITLE) ?: ""
        val text = inputData.getString(DataKeys.TEXT) ?: ""
        notificationService.showNotification(title = title, text = text)
        return Result.success()
    }
}
