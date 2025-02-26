package ru.vafeen.whisperoftasks.data.planner

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import org.koin.core.component.KoinComponent
import ru.vafeen.whisperoftasks.domain.domain_models.Reminder
import ru.vafeen.whisperoftasks.domain.notification.usecase.ShowNotificationTaskUseCase

class ReminderReceiver : BroadcastReceiver(), KoinComponent {
    private val showNotificationTaskUseCase = getKoin().get<ShowNotificationTaskUseCase>()
    override fun onReceive(context: Context, intent: Intent) {
        try {
            showNotificationTaskUseCase.invoke(
                intent.getStringExtra(TITLE_KEY).toString(),
                intent.getStringExtra(TEXT_KEY).toString()
            )
            Toast.makeText(context, "Ok", Toast.LENGTH_LONG).show()
            Log.d("receiver", "ok")
        } catch (e: Exception) {
            Toast.makeText(context, e.stackTrace.toString(), Toast.LENGTH_LONG).show()
            Log.d("receiver", e.stackTrace.toString())
        }
    }

    companion object {
        private const val TITLE_KEY = "TITLE"
        private const val TEXT_KEY = "TEXT"
        fun intentWithExtras(context: Context, reminder: Reminder): Intent =
            Intent(context, ReminderReceiver::class.java).also {
                it.putExtra(TITLE_KEY, reminder.title)
                it.putExtra(TEXT_KEY, reminder.text)
            }
    }
}