package ru.vafeen.whisperoftasks.ui.common.components.ui_utils

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.vafeen.whisperoftasks.R
import ru.vafeen.whisperoftasks.noui.duration.RepeatDuration
import ru.vafeen.whisperoftasks.noui.local_database.entity.Reminder
import ru.vafeen.whisperoftasks.ui.common.components.EventCreation
import ru.vafeen.whisperoftasks.ui.theme.FontSize
import ru.vafeen.whisperoftasks.ui.theme.Theme
import java.time.LocalDate

@Composable
fun Reminder.ReminderDataString(
    context: Context,
    dateOfThisPage: LocalDate,
    modifier: Modifier = Modifier,
    viewModel: EventCreation,
) {
    var isDialogDeleteShows by remember {
        mutableStateOf(false)
    }
    if (isDialogDeleteShows) RemoveReminderDialog(onDismissRequest = {
        isDialogDeleteShows = false
    }) {
        viewModel.removeEvent(reminder = this)
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        colors = CardDefaults.cardColors(containerColor = Theme.colors.buttonColor)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Checkbox(
                checked = dateOfDone != null && (dateOfDone >= dateOfThisPage ||
                        this@ReminderDataString.repeatDuration == RepeatDuration.NoRepeat),
                onCheckedChange = {
                    viewModel.updateEvent(
                        copy(
                            dateOfDone = if (this@ReminderDataString.repeatDuration != RepeatDuration.NoRepeat) {
                                if (dateOfDone == dateOfThisPage) null else {
                                    if (dateOfDone != null)
                                        Toast.makeText(
                                            context,
                                            "Помечено выполненным до сегодня",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    dateOfThisPage
                                }
                            } else {
                                if (dateOfDone != null) null else dateOfThisPage
                            }
                        )
                    )
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = Theme.colors.oppositeTheme,
                    uncheckedColor = Theme.colors.oppositeTheme,
                    checkmarkColor = Theme.colors.singleTheme,
                )
            )
            Column(
                modifier = Modifier.weight(1f)
            ) {
                TextForThisTheme(text = title, fontSize = FontSize.big22)
                TextForThisTheme(text = text, fontSize = FontSize.medium19)
                Row {
                    TextForThisTheme(
                        text = "${stringResource(id = this@ReminderDataString.repeatDuration.resourceName)} ${
                            this@ReminderDataString.dt.toLocalTime().let {
                                if (it.hour != 0 && it.minute != 0)
                                    "${stringResource(id = R.string.`in`)} $it"
                                else ""
                            }
                        }", fontSize = FontSize.medium19
                    )
                    if (this@ReminderDataString.isNotificationNeeded)
                        Icon(
                            painter = painterResource(R.drawable.message),
                            contentDescription = "message",
                            tint = Theme.colors.oppositeTheme
                        )
                }
            }
            IconButton(onClick = { isDialogDeleteShows = true }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete reminder ",
                    tint = Theme.colors.oppositeTheme
                )
            }
        }
    }
}