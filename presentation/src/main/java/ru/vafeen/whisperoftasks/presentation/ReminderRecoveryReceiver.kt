package ru.vafeen.whisperoftasks.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.koin.java.KoinJavaComponent.getKoin
import org.koin.java.KoinJavaComponent.inject
import ru.vafeen.whisperoftasks.data.duration.RepeatDuration
import ru.vafeen.whisperoftasks.domain.noui.notification.NotificationService
import ru.vafeen.whisperoftasks.domain.noui.notification.NotificationService.Companion.createNotificationReminderRecovery
import ru.vafeen.whisperoftasks.domain.planner.Scheduler
import ru.vafeen.whisperoftasks.domain.usecase.GetAllAsFlowRemindersUseCase
import ru.vafeen.whisperoftasks.resources.R
import java.time.LocalDateTime

class ReminderRecoveryReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            val context: Context = getKoin().get()
            val getAllAsFlowRemindersUseCase: GetAllAsFlowRemindersUseCase = getKoin().get()

            val scheduler: Scheduler by inject(
                clazz = Scheduler::class.java
            )
            val notificationService: NotificationService by inject(
                clazz = NotificationService::class.java
            )
            val dt = LocalDateTime.now()
            runBlocking {
                getAllAsFlowRemindersUseCase.invoke().first().forEach { reminder ->
                    scheduler.cancelWork(reminder = reminder, intent = intent)
                    when {
                        reminder.repeatDuration == RepeatDuration.NoRepeat && reminder.dt >= dt -> {
                            scheduler.planOneTimeWork(reminder = reminder, intent = intent)
                        }

                        reminder.repeatDuration == RepeatDuration.EveryDay || reminder.repeatDuration == RepeatDuration.EveryWeek -> {
                            scheduler.planRepeatWork(reminder = reminder, intent = intent)
                        }
                    }
                }
            }
            notificationService.showNotification(
                createNotificationReminderRecovery(
                    intent = intent,
                    title = context.getString(R.string.reminder_recovery),
                    text = context.getString(R.string.reminders_restored)
                )
            )
        }
    }
}