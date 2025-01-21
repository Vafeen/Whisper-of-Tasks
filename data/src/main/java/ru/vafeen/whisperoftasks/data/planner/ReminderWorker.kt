package ru.vafeen.whisperoftasks.data.planner


import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import org.koin.core.component.KoinComponent
import ru.vafeen.whisperoftasks.domain.database.repository.ReminderLocalRepository
import ru.vafeen.whisperoftasks.domain.notification.usecase.ShowNotificationTaskUseCase

internal class ReminderWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams), KoinComponent {

    private val reminderRepository: ReminderLocalRepository = getKoin().get()
    private val showNotificationTaskUseCase: ShowNotificationTaskUseCase = getKoin().get()

    override suspend fun doWork(): Result {
        val reminderId = inputData.getInt(SchedulerExtra.ID_OF_REMINDER, -1)

        return if (reminderId != -1) {
            try {
                // Получаем напоминание из репозитория
                val reminder = reminderRepository.getReminderByIdOfReminder(reminderId)
                reminder?.let {
                    // Создаем уведомление и показываем его через NotificationService.
                    showNotificationTaskUseCase.invoke(it)
                }
                Result.success()
            } catch (e: Exception) {
                Result.failure()
            }
        } else {
            Result.failure()
        }
    }
}
