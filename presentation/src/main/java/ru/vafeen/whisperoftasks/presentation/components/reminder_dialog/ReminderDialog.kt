package ru.vafeen.whisperoftasks.presentation.components.reminder_dialog


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.runtime.collectAsState
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
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import ru.vafeen.whisperoftasks.domain.domain_models.Reminder
import ru.vafeen.whisperoftasks.domain.duration.RepeatDuration
import ru.vafeen.whisperoftasks.domain.utils.localTimeNowHHMM
import ru.vafeen.whisperoftasks.domain.utils.nullTime
import ru.vafeen.whisperoftasks.domain.utils.withDate
import ru.vafeen.whisperoftasks.domain.utils.withTime
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.DefaultDialog
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.TextForThisTheme
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.customMainColorOrDefault
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.time_picker.MyDateTimePicker
import ru.vafeen.whisperoftasks.presentation.ui.theme.FontSize
import ru.vafeen.whisperoftasks.presentation.ui.theme.Theme
import ru.vafeen.whisperoftasks.presentation.utils.DatePickerInfo
import ru.vafeen.whisperoftasks.presentation.utils.suitableColor
import ru.vafeen.whisperoftasks.resources.R

@Composable
internal fun ReminderDialog(newReminder: MutableState<Reminder>, onDismissRequest: () -> Unit) {
    val viewModel: ReminderDialogViewModel = koinViewModel()
    val settings by viewModel.settings.collectAsState()
    val mainColor = settings.customMainColorOrDefault(isSystemInDarkTheme())
    val startDateInPast by remember { mutableStateOf(DatePickerInfo.startDateInPast()) }
    val context = LocalContext.current
    val cor = rememberCoroutineScope()
    if (newReminder.value.dt.toLocalDate() < startDateInPast) {
        newReminder.value = newReminder.value.copy(
            dt = newReminder.value.dt.withDate(localDate = startDateInPast)
        )
        LaunchedEffect(null) {
            viewModel.insertReminder(newReminder.value)
        }
    }
    val lastReminder by remember { mutableStateOf(newReminder.value) }
    val focusRequester1 = remember { FocusRequester() }
    val focusRequester2 = remember { FocusRequester() }

    var selectedDateTime by remember {
        mutableStateOf(newReminder.value.dt)
    }
    var isChoosingDurationInProcess by remember { mutableStateOf(false) }


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
                    text = stringResource(id = R.string.notification), fontSize = FontSize.medium19
                )
                Checkbox(
                    checked = newReminder.value.isNotificationNeeded, onCheckedChange = { checked ->
                        selectedDateTime = selectedDateTime.withTime(
                            if (checked) localTimeNowHHMM().plusMinutes(5)
                            else nullTime
                        )
                        newReminder.value = newReminder.value.copy(
                            isNotificationNeeded = !newReminder.value.isNotificationNeeded,
                            dt = selectedDateTime
                        )

                    }, colors = CheckboxDefaults.colors(
                        checkedColor = mainColor,
                        uncheckedColor = mainColor,
                        checkmarkColor = mainColor.suitableColor(),
                    )
                )
            }
            MyDateTimePicker(isTimeNeeded = newReminder.value.isNotificationNeeded,
                initialDate = newReminder.value.dt.toLocalDate(),
                onDateSelected = { date ->
                    selectedDateTime = selectedDateTime.withDate(date)
                    newReminder.value = newReminder.value.copy(dt = selectedDateTime)
                },
                initialTime = selectedDateTime.toLocalTime(),
                onTimeSelected = { time ->
                    selectedDateTime = selectedDateTime.withTime(time)
                    newReminder.value = newReminder.value.copy(
                        dt = selectedDateTime
                    )
                })
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
                    ),
                    expanded = isChoosingDurationInProcess,
                    onDismissRequest = { isChoosingDurationInProcess = false }) {
                    for (item in RepeatDuration.all) {
                        DropdownMenuItem(text = {
                            TextForThisTheme(
                                text = stringResource(id = item.resourceName),
                                fontSize = FontSize.medium19
                            )
                        }, onClick = {
                            newReminder.value = newReminder.value.copy(repeatDuration = item)
                            isChoosingDurationInProcess = false
                        })
                    }
                }
            }

            Button(
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = mainColor
                ),
                enabled = (lastReminder != newReminder.value) && newReminder.value.let { it.text.isNotEmpty() || it.title.isNotEmpty() },
                onClick = {
                    cor.launch {
                        viewModel.updateReminderAndEventDependsOnChangedFields(
                            lastReminder,
                            newReminder.value
                        )
                        onDismissRequest()
                    }
                },
            ) {
                Text(
                    color = mainColor.suitableColor(),
                    text = viewModel.mainButtonText(context, selectedDateTime, newReminder.value)
                )
            }
        }
        LaunchedEffect(null) {
            focusRequester1.requestFocus()
        }
    }
}