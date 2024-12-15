package ru.vafeen.whisperoftasks.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.getKoin
import ru.vafeen.whisperoftasks.domain.noui.notification.NotificationService
import ru.vafeen.whisperoftasks.domain.planner.SchedulerExtra
import ru.vafeen.whisperoftasks.domain.usecase.GetReminderByIdOfReminderUseCase

class NotificationReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationService: NotificationService = getKoin().get()
        val getReminderByIdOfReminderUseCase = getKoin().get<GetReminderByIdOfReminderUseCase>()
        val idOfReminder = intent.getIntExtra(
            SchedulerExtra.ID_OF_REMINDER,
            -1
        )
        CoroutineScope(Dispatchers.IO).launch {
            val reminder = getReminderByIdOfReminderUseCase.invoke(
                idOfReminder = idOfReminder
            )
            reminder?.let {
                notificationService.showNotification(
                    NotificationService.createNotificationTask(
                        intent = intent,
                        title = it.title,
                        text = it.text
                    )
                )
            }
        }
    }
}
