package ru.vafeen.reminder.ui.common.components.ui_utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.res.stringResource
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
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime


@Composable
fun ReminderDialog(
    newReminder: MutableState<Reminder>,
    onDismissRequest: () -> Unit,
) {
    val lastReminder by remember { mutableStateOf(newReminder.value.toString()) }
    val cor = rememberCoroutineScope()
    val focusRequester1 = remember { FocusRequester() }
    val focusRequester2 = remember { FocusRequester() }
    val databaseRepository: DatabaseRepository by inject(
        clazz = DatabaseRepository::class.java
    )
    val eventCreator: EventCreator by inject(
        clazz = EventCreator::class.java
    )
    var mainButtonText by remember {
        mutableStateOf("")
    }
    var selectedDate by remember {
        mutableStateOf(
            newReminder.value.dt?.toLocalDate() ?: LocalDate.now()
        )
    }
    var selectedTime by remember {
        mutableStateOf(
            newReminder.value.dt?.toLocalTime() ?: LocalTime.now()
        )
    }
    var isChoosingDurationInProcess by remember { mutableStateOf(false) }
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
                value = newReminder.value.title,
                onValueChange = { newReminder.value = newReminder.value.copy(title = it) },
                label = { Text(text = "title") },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                modifier = Modifier.focusRequester(focusRequester2),
                value = newReminder.value.text,
                onValueChange = { newReminder.value = newReminder.value.copy(text = it) },
                label = { Text(text = "text") },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                TextForThisTheme(text = "Уведомление", fontSize = FontSize.medium19)
                Checkbox(
                    checked = newReminder.value.isNotificationNeeded == true,
                    onCheckedChange = {
                        newReminder.value =
                            newReminder.value.copy(isNotificationNeeded = newReminder.value.isNotificationNeeded != true)
                    })
            }
            MyDateTimePicker(
                isTimeNeeded = newReminder.value.isNotificationNeeded == true,
                initialDate = newReminder.value.dt?.toLocalDate() ?: LocalDate.now(),
                onDateSelected = { date ->
                    selectedDate = date
                    mainButtonText =
                        "Отправить ${selectedDate.getDateString()} в ${selectedTime.hour.getTimeDefaultStr()}:${selectedTime.minute.getTimeDefaultStr()}"
                    newReminder.value = newReminder.value.copy(
                        dt = LocalDateTime.of(
                            selectedDate,
                            selectedTime
                        )
                    )
                },
                initialTime = newReminder.value.dt?.toLocalTime()
                    ?: LocalTime.now()
                        .plusMinutes(5),
                onTimeSelected = { time ->
                    selectedTime = time
                    mainButtonText =
                        "Отправить ${selectedDate.getDateString()} в ${selectedTime.hour.getTimeDefaultStr()}:${selectedTime.minute.getTimeDefaultStr()}"
                    newReminder.value = newReminder.value.copy(
                        dt = LocalDateTime.of(
                            selectedDate,
                            selectedTime
                        )
                    )
                }
            )
            Box(modifier = Modifier.clickable {
                isChoosingDurationInProcess = true
            }) {
                TextForThisTheme(
                    text = stringResource(id = newReminder.value.repeatDuration.resourceName),
                    fontSize = FontSize.medium19
                )
                DropdownMenu(modifier = Modifier
                    .background(Theme.colors.singleTheme)
                    .border(
                        border = BorderStroke(
                            width = 2.dp, color = Theme.colors.oppositeTheme
                        )
                    ), expanded = isChoosingDurationInProcess,
                    onDismissRequest = { isChoosingDurationInProcess = false }) {
                    for (item in RepeatDuration.all) {
                        DropdownMenuItem(text = {
                            TextForThisTheme(
                                text = stringResource(id = item.resourceName),
                                fontSize = FontSize.medium19
                            )
                        },
                            onClick = {
                                newReminder.value = newReminder.value.copy(repeatDuration = item)
                                isChoosingDurationInProcess = false
                            })
                    }
                }
            }

            Button(
                enabled = (lastReminder != newReminder.value.toString()) &&
                        (newReminder.value.let { it.text.isNotEmpty() && it.title.isNotEmpty() } == true),
                onClick = {
                    cor.launch(Dispatchers.IO) {
                        newReminder.value = newReminder.value.copy(
                            idOfReminder = databaseRepository.getAllRemindersAsFlow().first()
                                .map { it.idOfReminder }.generateID(),
                            dt = newReminder.value.dt?.let {
                                LocalDateTime.of(
                                    it.toLocalDate(),
                                    if (!newReminder.value.isNotificationNeeded) LocalTime.of(0, 0)
                                    else LocalTime.of(it.hour, it.minute)
                                )
                            }
                        )
                        newReminder.value.let {
                            eventCreator.planeEvent(it)
                        }
                        onDismissRequest()
                    }
                },
            ) {
                TextForThisTheme(
                    text = if (newReminder.value.isNotificationNeeded == true) mainButtonText else "Добавить в список",
                    fontSize = FontSize.medium19
                )
            }
        }
    }
}