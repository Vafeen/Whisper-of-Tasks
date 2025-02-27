package ru.vafeen.whisperoftasks.data.planner


import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
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
                .setInitialDelay(calculateDelayToReminder(reminder.dt), TimeUnit.MILLISECONDS)
                .build()

        fun periodicRequestWithData(reminder: Reminder) =
            PeriodicWorkRequestBuilder<ReminderWorker>(reminder.repeatDuration.duration)
                .setInputData(reminder.workerData())
                .setInitialDelay(calculateDelayToReminder(reminder.dt), TimeUnit.MILLISECONDS)
                .build()


    }
}