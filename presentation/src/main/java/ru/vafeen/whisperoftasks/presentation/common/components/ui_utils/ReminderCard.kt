package ru.vafeen.whisperoftasks.presentation.common.components.ui_utils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.vafeen.whisperoftasks.domain.domain_models.Reminder
import ru.vafeen.whisperoftasks.presentation.ui.theme.FontSize
import ru.vafeen.whisperoftasks.presentation.ui.theme.Theme


@Composable
fun Reminder.ReminderCard() {
    Card(
        modifier = Modifier.padding(5.dp),
        colors = CardDefaults.cardColors(containerColor = Theme.colors.buttonColor)
    ) {
        Column(modifier = Modifier.padding(5.dp)) {
            TextForThisTheme(text = title, fontSize = FontSize.medium19, maxLines = 5)
            Spacer(modifier = Modifier.height(5.dp))
            TextForThisTheme(text = text, fontSize = FontSize.small17, maxLines = 5)
        }
    }
}