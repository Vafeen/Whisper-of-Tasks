package ru.vafeen.whisperoftasks.data.planner

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.flow.first
import org.koin.core.component.KoinComponent
import ru.vafeen.whisperoftasks.domain.database.usecase.GetAllAsFlowRemindersUseCase
import ru.vafeen.whisperoftasks.domain.duration.RepeatDuration
import ru.vafeen.whisperoftasks.domain.notification.usecase.NotificationReminderRecoveryUseCase
import ru.vafeen.whisperoftasks.domain.planner.Scheduler
import java.time.LocalDateTime

internal class ReminderRecoveryWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams), KoinComponent {

    private val getAsFlowRemindersUseCase: GetAllAsFlowRemindersUseCase = getKoin().get()
    private val scheduler: Scheduler = getKoin().get()
    private val notificationReminderRecoveryUseCase: NotificationReminderRecoveryUseCase =
        getKoin().get()

    override suspend fun doWork(): Result = try {
        // Получение напоминаний и выполнение задач
        val reminders = getAsFlowRemindersUseCase.invoke().first()
        val dt = LocalDateTime.now()
        reminders.forEach { reminder ->
            scheduler.cancelWork(reminder)
            if (reminder.dt >= dt || reminder.repeatDuration != RepeatDuration.NoRepeat) {
                scheduler.planWork(reminder)
            }
        }
        notificationReminderRecoveryUseCase.invoke()
        Result.success()
    } catch (e: Exception) {
        Result.failure()
    }
}