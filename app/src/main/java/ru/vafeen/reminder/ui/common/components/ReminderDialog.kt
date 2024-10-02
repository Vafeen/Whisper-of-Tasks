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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import ru.vafeen.reminder.noui.EventCreator
import ru.vafeen.reminder.noui.duration.RepeatDuration
import ru.vafeen.reminder.noui.local_database.DatabaseRepository
import ru.vafeen.reminder.noui.local_database.entity.Reminder
import ru.vafeen.reminder.ui.theme.FontSize
import ru.vafeen.reminder.ui.theme.Theme
import ru.vafeen.reminder.utils.generateID
import ru.vafeen.reminder.utils.getDateString
import ru.vafeen.reminder.utils.getTimeDefaultStr
import java.time.LocalDateTime
import java.time.LocalTime

@Composable
fun ReminderDialog(
    newReminder: MutableState<Reminder?>,
    onDismissRequest: () -> Unit
) {
    val untitled = "untitled"
    val cor = rememberCoroutineScope()
    val focusRequester1 = remember { FocusRequester() }
    val focusRequester2 = remember { FocusRequester() }
    val databaseRepository: DatabaseRepository by inject(
        clazz = DatabaseRepository::class.java
    )
    val eventCreator: EventCreator by inject(
        clazz = EventCreator::class.java
    )
    if (newReminder.value == null)
        newReminder.value = Reminder(
            title = "", text = "", dt = LocalDateTime.now().plusMinutes(5),
            idOfReminder = 0,
            repeatDuration = RepeatDuration.NoRepeat
        )

    var mainButtonText by remember {
        mutableStateOf("")
    }
    LaunchedEffect(null) {
        focusRequester1.requestFocus()
    }
    DefaultDialog(onDismissRequest = onDismissRequest) { dp ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Theme.colors.singleTheme)
                .padding(dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                modifier = Modifier.focusRequester(focusRequester1),
                value = newReminder.value?.title ?: untitled,
                onValueChange = { newReminder.value = newReminder.value!!.copy(title = it) },
                label = { Text(text = "title") },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                modifier = Modifier.focusRequester(focusRequester2),
                value = newReminder.value?.text ?: untitled,
                onValueChange = { newReminder.value = newReminder.value?.copy(text = it) },
                label = { Text(text = "text") },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
            )
            Spacer(modifier = Modifier.height(20.dp))

            MyTimePicker(initialTime = LocalTime.now().plusMinutes(5)) { dt ->
                mainButtonText =
                    "Отправить ${dt.getDateString()} в ${dt.hour.getTimeDefaultStr()}:${dt.minute.getTimeDefaultStr()}"
                newReminder.value = newReminder.value?.copy(dt = dt)
            }

            Button(
                onClick = {
                    cor.launch(Dispatchers.IO) {
                        newReminder.value = newReminder.value?.copy(
                            idOfReminder = databaseRepository.getAllRemindersAsFlow().first()
                                .map { it.idOfReminder }.generateID()
                        )
                        newReminder.value?.let {
                            eventCreator.addEvent(it)
                        }
                        onDismissRequest()
                    }
                },
                enabled = newReminder.value?.let { it.text.isNotEmpty() && it.title.isNotEmpty() }
                    ?: false) {
                TextForThisTheme(text = mainButtonText, fontSize = FontSize.medium19)
            }
        }
    }
}