package ru.vafeen.reminder.ui.common.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.vafeen.reminder.noui.local_database.entity.Reminder
import ru.vafeen.reminder.ui.theme.FontSize
import ru.vafeen.reminder.ui.theme.Theme

@Composable
fun Reminder.ReminderDataString() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        colors = CardDefaults.cardColors(containerColor = Theme.colors.buttonColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            TextForThisTheme(text = this@ReminderDataString.title, fontSize = FontSize.big)
            TextForThisTheme(text = this@ReminderDataString.text, fontSize = FontSize.big)
        }
    }
}