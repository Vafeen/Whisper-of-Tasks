package ru.vafeen.reminder.noui.planner

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import ru.vafeen.reminder.noui.local_database.DatabaseRepository
import ru.vafeen.reminder.noui.notification.NotificationService

class ReminderRecoveryReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            val databaseRepository: DatabaseRepository by inject(
                clazz = DatabaseRepository::class.java
            )
            val scheduler: Scheduler by inject(
                clazz = Scheduler::class.java
            )
            val notificationService: NotificationService by inject(
                clazz = NotificationService::class.java
            )
            CoroutineScope(Dispatchers.IO).launch {
                for (reminder in databaseRepository.getAllRemindersAsFlow().first()) {
                    scheduler.cancelWork(reminder = reminder)
                    scheduler.planOneTimeWork(reminder = reminder)
                }
            }
            notificationService.showNotification(
                "Восстановление будильников",
                "Будильники восстановлены!"
            )
        }
    }

}