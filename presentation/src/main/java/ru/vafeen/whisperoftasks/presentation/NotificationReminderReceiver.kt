package ru.vafeen.whisperoftasks.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import ru.vafeen.whisperoftasks.data.local_database.DatabaseRepository
import ru.vafeen.whisperoftasks.domain.noui.notification.NotificationService
import ru.vafeen.whisperoftasks.domain.noui.planner.SchedulerExtra

class NotificationReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationService: NotificationService by inject(
            clazz = NotificationService::class.java
        )
        val databaseRepository: DatabaseRepository by inject(
            clazz = DatabaseRepository::class.java
        )
        val idOfReminder = intent.getIntExtra(
            SchedulerExtra.ID_OF_REMINDER,
            -1
        )
        CoroutineScope(Dispatchers.IO).launch {
            val reminder = databaseRepository.getReminderByIdOfReminder(
                idOfReminder = idOfReminder
            )
            reminder?.let {
                notificationService.showNotification(
                    NotificationService.createNotificationTask(
                        intent=intent,
                        title = it.title,
                        text = it.text
                    )
                )
            }
        }
    }
}
