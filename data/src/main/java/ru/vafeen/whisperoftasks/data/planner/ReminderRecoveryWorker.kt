package ru.vafeen.whisperoftasks.data.planner
//
//import android.content.Context
//import androidx.work.CoroutineWorker
//import androidx.work.WorkerParameters
//import kotlinx.coroutines.flow.first
//import org.koin.core.component.KoinComponent
//import ru.vafeen.whisperoftasks.domain.database.usecase.GetAllAsFlowRemindersUseCase
//import ru.vafeen.whisperoftasks.domain.duration.RepeatDuration
//import ru.vafeen.whisperoftasks.domain.notification.usecase.NotificationReminderRecoveryUseCase
//import ru.vafeen.whisperoftasks.domain.planner.Scheduler
//import ru.vafeen.whisperoftasks.domain.planner.usecase.ReminderRecoveryUseCase
//import java.time.LocalDateTime
//
//internal class ReminderRecoveryWorker(
//    context: Context,
//    workerParams: WorkerParameters
//) : CoroutineWorker(context, workerParams), KoinComponent {
//    private val notificationReminderRecoveryUseCase: NotificationReminderRecoveryUseCase =
//        getKoin().get()
//    private val reminderRecoveryUseCase: ReminderRecoveryUseCase = getKoin().get()
//    override suspend fun doWork(): Result = try {
//        reminderRecoveryUseCase.invoke()
//        notificationReminderRecoveryUseCase.invoke()
//        Result.success()
//    } catch (e: Exception) {
//        Result.failure()
//    }
//}