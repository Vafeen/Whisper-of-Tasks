package ru.vafeen.whisperoftasks.data.planner.alarm_manager


import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import org.koin.core.component.KoinComponent
import ru.vafeen.whisperoftasks.domain.domain_models.Reminder
import ru.vafeen.whisperoftasks.domain.notification.usecase.ShowNotificationTaskUseCase

class AlarmManagerReminderReceiver : BroadcastReceiver(), KoinComponent {
    private val showNotificationTaskUseCase: ShowNotificationTaskUseCase = getKoin().get()
    override fun onReceive(context: Context, intent: Intent) {
        val id = intent.getIntExtra(ID_OF_REMINDER, -1)
        if (id == -1) return
        val title = intent.getStringExtra(TITLE).toString()
        val text = intent.getStringExtra(TEXT).toString()
        showNotificationTaskUseCase.invoke(id, title, text)
    }

    companion object {
        private const val ID_OF_REMINDER = "ID_OF_REMINDER"
        private const val TITLE = "TITLE"
        private const val TEXT = "TEXT"
        fun pendingIntentWithData(context: Context, reminder: Reminder): PendingIntent =
            PendingIntent.getBroadcast(
                context,
                reminder.idOfReminder,
                Intent(context, AlarmManagerReminderReceiver::class.java).apply {
                    putExtra(ID_OF_REMINDER, reminder.idOfReminder)
                    putExtra(TITLE, reminder.title)
                    putExtra(TEXT, reminder.text)
                },
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

    }
}