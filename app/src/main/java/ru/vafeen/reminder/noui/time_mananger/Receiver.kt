package ru.vafeen.reminder.noui.time_mananger

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Действия, которые нужно выполнить при срабатывании будильника
        Toast.makeText(context, "Будильник сработал!", Toast.LENGTH_SHORT).show()
    }
}