package ru.vafeen.whisperoftasks.ui.common.components.ui_utils

import android.util.Log
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import ru.vafeen.whisperoftasks.R
import ru.vafeen.whisperoftasks.noui.EventCreator
import ru.vafeen.whisperoftasks.noui.duration.RepeatDuration
import ru.vafeen.whisperoftasks.noui.local_database.DatabaseRepository
import ru.vafeen.whisperoftasks.noui.local_database.entity.Reminder
import ru.vafeen.whisperoftasks.ui.theme.FontSize
import ru.vafeen.whisperoftasks.ui.theme.Theme
import ru.vafeen.whisperoftasks.utils.generateID
import ru.vafeen.whisperoftasks.utils.getDateStringWithWeekOfDay
import ru.vafeen.whisperoftasks.utils.getTimeDefaultStr
import ru.vafeen.whisperoftasks.utils.suitableColor
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Composable
fun ReminderDialog(
    newReminder: MutableState<Reminder>,
    onDismissRequest: () -> Unit,
) {
    val context = LocalContext.current
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
            newReminder.value.dt.toLocalDate() ?: LocalDate.now()
        )
    }
    var selectedTime by remember {
        mutableStateOf(
            newReminder.value.dt.toLocalTime()?.let {
                Log.d("reminder", it.toString())
                if (it.hour == 0 && it.minute == 0) LocalTime.now()
                else it
            } ?: LocalTime.now()
        )
    }
    var isChoosingDurationInProcess by remember { mutableStateOf(false) }
    LaunchedEffect(null) {
        focusRequester1.requestFocus()
    }
    fun createMainButtonText(): String = "${
        ContextCompat.getString(
            context,
            R.string.send
        )
    } ${selectedDate.getDateStringWithWeekOfDay(context = context)} ${
        ContextCompat.getString(
            context,
            R.string.`in`
        )
    } ${selectedTime.hour.getTimeDefaultStr()}:${selectedTime.minute.getTimeDefaultStr()}"

    val colors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = Theme.colors.oppositeTheme,
        unfocusedTextColor = Theme.colors.oppositeTheme,
        focusedBorderColor = Theme.colors.oppositeTheme,
        unfocusedBorderColor = Theme.colors.oppositeTheme,
        focusedLabelColor = Theme.colors.oppositeTheme,
        unfocusedLabelColor = Theme.colors.oppositeTheme,
        cursorColor = Theme.colors.oppositeTheme
    )
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
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                colors = colors
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                modifier = Modifier.focusRequester(focusRequester2),
                value = newReminder.value.text,
                onValueChange = { newReminder.value = newReminder.value.copy(text = it) },
                label = { Text(text = "text") },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                colors = colors,
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                TextForThisTheme(
                    text = stringResource(id = R.string.notification),
                    fontSize = FontSize.medium19
                )
                Checkbox(
                    checked = newReminder.value.isNotificationNeeded == true,
                    onCheckedChange = {
                        newReminder.value =
                            newReminder.value.copy(isNotificationNeeded = !newReminder.value.isNotificationNeeded)
                    }, colors = CheckboxDefaults.colors(
                        checkedColor = Theme.colors.oppositeTheme,
                        uncheckedColor = Theme.colors.oppositeTheme,
                        checkmarkColor = Theme.colors.singleTheme,
                    )
                )
            }
            MyDateTimePicker(
                isTimeNeeded = newReminder.value.isNotificationNeeded == true,
                initialDate = selectedDate,
                onDateSelected = { date ->
                    Log.d("reminder", "onDateSelected")
                    if (date != selectedTime) {
                        selectedDate = date
                        mainButtonText = createMainButtonText()

                        newReminder.value = newReminder.value.copy(
                            dt = LocalDateTime.of(
                                selectedDate,
                                selectedTime
                            )
                        )
                    }
                },
                initialTime = selectedTime,
                onTimeSelected = { time ->
                    Log.d("reminder", "onTimeSelected")
                    if (time != selectedTime) {
                        selectedTime = time
                        mainButtonText = createMainButtonText()
                        newReminder.value = newReminder.value.copy(
                            dt = LocalDateTime.of(
                                selectedDate,
                                selectedTime
                            )
                        )
                    }
                }
            )
            Box(modifier = Modifier
                .padding(vertical = 5.dp)
                .clickable {
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
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Theme.colors.mainColor
                ),
                enabled = (lastReminder.also {
                    Log.d("reminder", "last = $it")
                } != newReminder.value.toString().also {
                    Log.d("reminder", "new = $it")
                }) &&
                        (newReminder.value.let { it.text.isNotEmpty() || it.title.isNotEmpty() } == true),
                onClick = {
                    cor.launch(Dispatchers.IO) {
                        newReminder.value = newReminder.value.copy(
                            idOfReminder = databaseRepository.getAllRemindersAsFlow().first()
                                .map { it.idOfReminder }.generateID(),
                            dt = newReminder.value.dt.let {
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
                Text(
                    color = Theme.colors.mainColor.suitableColor(),
                    text = if (newReminder.value.isNotificationNeeded == true) mainButtonText else stringResource(
                        id = R.string.add_to_list
                    ),
                    fontSize = FontSize.medium19
                )
            }
        }
    }
}