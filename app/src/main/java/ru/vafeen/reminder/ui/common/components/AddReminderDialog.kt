package ru.vafeen.reminder.ui.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import ru.vafeen.reminder.noui.local_database.entity.Reminder
import ru.vafeen.reminder.ui.theme.Theme
import java.time.LocalDateTime

@Composable
fun AddReminderDialog(onDismissRequest: () -> Unit, addReminder: (Reminder) -> Unit) {
    val focusRequester1 = remember { FocusRequester() }
    val focusRequester2 = remember { FocusRequester() }
    var newReminder by remember {
        mutableStateOf(Reminder(title = "", text = "", dt = LocalDateTime.now().plusMinutes(5)))
    }
    LaunchedEffect(null) {
        focusRequester1.requestFocus()
    }
    DefaultDialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Theme.colors.singleTheme)
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                modifier = Modifier.focusRequester(focusRequester1),
                value = newReminder.title,
                onValueChange = { newReminder = newReminder.copy(title = it) },
                label = { Text(text = "title") },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                modifier = Modifier.focusRequester(focusRequester2),
                value = newReminder.text,
                onValueChange = { newReminder = newReminder.copy(text = it) },
                label = { Text(text = "text") },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
            )
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