package ru.vafeen.whisperoftasks.presentation.common.components.ui_utils

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
import org.koin.compose.getKoin
import ru.vafeen.whisperoftasks.data.duration.RepeatDuration
import ru.vafeen.whisperoftasks.domain.models.Reminder
import ru.vafeen.whisperoftasks.domain.planner.EventCreation
import ru.vafeen.whisperoftasks.domain.usecase.GetAllAsFlowRemindersUseCase
import ru.vafeen.whisperoftasks.domain.usecase.InsertAllRemindersUseCase
import ru.vafeen.whisperoftasks.domain.utils.generateID
import ru.vafeen.whisperoftasks.domain.utils.getDateStringWithWeekOfDay
import ru.vafeen.whisperoftasks.domain.utils.getTimeDefaultStr
import ru.vafeen.whisperoftasks.domain.utils.localTimeNowHHMM
import ru.vafeen.whisperoftasks.domain.utils.withDate
import ru.vafeen.whisperoftasks.domain.utils.withTime
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.time_picker.MyDateTimePicker
import ru.vafeen.whisperoftasks.presentation.ui.theme.FontSize
import ru.vafeen.whisperoftasks.presentation.ui.theme.Theme
import ru.vafeen.whisperoftasks.presentation.utils.DatePickerInfo
import ru.vafeen.whisperoftasks.presentation.utils.suitableColor
import ru.vafeen.whisperoftasks.resources.R
import java.time.LocalTime

@Composable
fun ReminderDialog(
    newReminder: MutableState<Reminder>,
    onDismissRequest: () -> Unit,
    eventCreation: EventCreation,
) {
    val startDateInPast by remember { mutableStateOf(DatePickerInfo.startDateInPast()) }
    val context = LocalContext.current
    val getAllAsFlowRemindersUseCase = getKoin().get<GetAllAsFlowRemindersUseCase>()
    val insertAllRemindersUseCase = getKoin().get<InsertAllRemindersUseCase>()
    if (newReminder.value.dt.toLocalDate() < startDateInPast) {
        newReminder.value = newReminder.value.copy(
            dt = newReminder.value.dt.withDate(localDate = startDateInPast)
        )
        LaunchedEffect(null) {
            insertAllRemindersUseCase.invoke(
                newReminder.value
            )
        }
    }
    val lastReminder by remember { mutableStateOf(newReminder.value.toString()) }
    val cor = rememberCoroutineScope()
    val nullTime = LocalTime.of(0, 0)
    val focusRequester1 = remember { FocusRequester() }
    val focusRequester2 = remember { FocusRequester() }

    var selectedDateTime by remember {
        mutableStateOf(newReminder.value.dt)
    }
    var isChoosingDurationInProcess by remember { mutableStateOf(false) }

    LaunchedEffect(null) {
        focusRequester1.requestFocus()
    }

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
                .background(Theme.colors.buttonColor)
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
                    checked = newReminder.value.isNotificationNeeded,
                    onCheckedChange = { checked ->
                        selectedDateTime = selectedDateTime.withTime(
                            if (checked) localTimeNowHHMM().plusMinutes(5)
                            else nullTime
                        )
                        Log.d("slt", selectedDateTime.toString())
                        newReminder.value =
                            newReminder.value.copy(
                                isNotificationNeeded = !newReminder.value.isNotificationNeeded,
                                dt = selectedDateTime
                            )

                    }, colors = CheckboxDefaults.colors(
                        checkedColor = Theme.colors.oppositeTheme,
                        uncheckedColor = Theme.colors.oppositeTheme,
                        checkmarkColor = Theme.colors.singleTheme,
                    )
                )
            }
            MyDateTimePicker(
                isTimeNeeded = newReminder.value.isNotificationNeeded,
                initialDate = newReminder.value.dt.toLocalDate(),
                onDateSelected = { date ->
                    selectedDateTime = selectedDateTime.withDate(date)
                    newReminder.value = newReminder.value.copy(dt = selectedDateTime)
                },
                initialTime = selectedDateTime.toLocalTime(),//newReminder.value.dt.toLocalTime(),
                onTimeSelected = { time ->
                    selectedDateTime = selectedDateTime.withTime(time)
                    newReminder.value = newReminder.value.copy(
                        dt = selectedDateTime
                    )
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
                enabled = (lastReminder != newReminder.value.toString()) && newReminder.value.let { it.text.isNotEmpty() || it.title.isNotEmpty() },
                onClick = {
                    cor.launch(Dispatchers.IO) {
                        newReminder.value = newReminder.value.copy(
                            idOfReminder = getAllAsFlowRemindersUseCase.invoke().first()
                                .map { it.idOfReminder }.generateID(),
                        )
                        eventCreation.updateEvent(newReminder.value)
                        onDismissRequest()
                    }
                },
            ) {
                Text(
                    color = Theme.colors.mainColor.suitableColor(),
                    text = if (newReminder.value.isNotificationNeeded) "${
                        ContextCompat.getString(
                            context,
                            R.string.send
                        )
                    } ${selectedDateTime.getDateStringWithWeekOfDay(context = context)} ${
                        ContextCompat.getString(
                            context,
                            R.string.`in`
                        )
                    } ${selectedDateTime.hour.getTimeDefaultStr()}:${selectedDateTime.minute.getTimeDefaultStr()}" else stringResource(
                        id = R.string.add_to_list
                    ),
                    fontSize = FontSize.medium19
                )
            }
        }
    }
}