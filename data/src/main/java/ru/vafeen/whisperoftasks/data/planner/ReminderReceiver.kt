package ru.vafeen.whisperoftasks.data.planner

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import org.koin.core.component.KoinComponent
import ru.vafeen.whisperoftasks.domain.domain_models.Reminder
import ru.vafeen.whisperoftasks.domain.notification.usecase.ShowNotificationTaskUseCase

class ReminderReceiver : BroadcastReceiver(), KoinComponent {
    private val showNotificationTaskUseCase = getKoin().get<ShowNotificationTaskUseCase>()
    override fun onReceive(context: Context, intent: Intent) {
        showNotificationTaskUseCase.invoke(
            intent.getStringExtra(TITLE_KEY).toString(),
            intent.getStringExtra(TEXT_KEY).toString()
        )
    }

    companion object {
        private const val TITLE_KEY = "TITLE"
        private const val TEXT_KEY = "TEXT"
        fun withExtras(intent: Intent, reminder: Reminder): Intent = Intent(intent).also {
            it.putExtra(TITLE_KEY, reminder.title)
            it.putExtra(TEXT_KEY, reminder.text)
        }
    }
}