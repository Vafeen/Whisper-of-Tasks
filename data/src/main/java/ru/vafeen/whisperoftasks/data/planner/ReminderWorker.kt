package ru.vafeen.whisperoftasks.data.planner


import android.content.Context
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkerParameters
import org.koin.core.component.KoinComponent
import ru.vafeen.whisperoftasks.domain.domain_models.Reminder
import ru.vafeen.whisperoftasks.domain.notification.usecase.ShowNotificationTaskUseCase
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.concurrent.TimeUnit

internal class ReminderWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams), KoinComponent {

    private val showNotificationTaskUseCase: ShowNotificationTaskUseCase = getKoin().get()

    override suspend fun doWork(): Result {
        val reminderId = inputData.getInt(ID_OF_REMINDER, -1)
        return if (reminderId != -1) {
            try {
                showNotificationTaskUseCase.invoke(idOfReminder = reminderId)
                Result.success()
            } catch (e: Exception) {
                Result.failure()
            }
        } else {
            Result.failure()
        }
    }

    companion object {
        private const val ID_OF_REMINDER = "ID_OF_REMINDER"
        private fun Reminder.workerData(): Data = Data.Builder()
            .putInt(ID_OF_REMINDER, idOfReminder)
            .build()

        private fun calculateDelayToReminder(dt: LocalDateTime): Long {
            val now = Instant.now().toEpochMilli()
            val reminderTime = dt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
            return reminderTime - now
        }


        fun oneTimeRequestWithData(reminder: Reminder) =
            OneTimeWorkRequestBuilder<ReminderWorker>()
                .setInputData(reminder.workerData())
                .setConstraints(createConstraints())
                .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                .setInitialDelay(calculateDelayToReminder(reminder.dt), TimeUnit.MILLISECONDS)
                .build()

        fun periodicRequestWithData(reminder: Reminder) =
            PeriodicWorkRequestBuilder<ReminderWorker>(reminder.repeatDuration.duration)
                .setInputData(reminder.workerData())
                .setConstraints(createConstraints())
                .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                .setInitialDelay(calculateDelayToReminder(reminder.dt), TimeUnit.MILLISECONDS)
                .build()
        /**
         * Создает ограничения для задач WorkManager.
         *
         * @return Объект Constraints с установленными ограничениями для выполнения задач.
         */
        private fun createConstraints(): Constraints {
            return Constraints.Builder()
                .setRequiresBatteryNotLow(false) // Разрешить выполнение при низком заряде батареи.
                .setRequiresDeviceIdle(false) // Разрешить выполнение, даже если устройство не в состоянии покоя.
                .setRequiredNetworkType(NetworkType.NOT_REQUIRED) // Не требовать подключения к сети.
                .build()
        }
    }
}