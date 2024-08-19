package ru.vafeen.reminder.ui.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.vafeen.reminder.noui.local_database.entity.Reminder
import ru.vafeen.reminder.ui.theme.Theme

@Composable
fun AddReminderDialog(onDismissRequest: () -> Unit, addReminder: (Reminder) -> Unit) {
    var newReminder by remember {
        mutableStateOf(Reminder(title = "", text = ""))
    }
    DefaultDialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Theme.colors.singleTheme)
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(value = newReminder.title,
                onValueChange = { newReminder = newReminder.copy(title = it) },
                label = { Text(text = "title") })
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = newReminder.text,
                onValueChange = { newReminder = newReminder.copy(text = it) },
                label = { Text(text = "text") })
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = {
                addReminder(newReminder)
                onDismissRequest()
            }, enabled = newReminder.let { it.text.isNotEmpty() && it.title.isNotEmpty() }) {
                TextForThisTheme(text = "Add")
            }
        }
    }
}