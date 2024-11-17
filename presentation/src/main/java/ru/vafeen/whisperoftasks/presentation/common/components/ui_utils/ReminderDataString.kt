package ru.vafeen.whisperoftasks.presentation.common.components.ui_utils

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.vafeen.whisperoftasks.data.R
import ru.vafeen.whisperoftasks.data.duration.RepeatDuration
import ru.vafeen.whisperoftasks.data.local_database.entity.Reminder
import ru.vafeen.whisperoftasks.domain.noui.EventCreation
import ru.vafeen.whisperoftasks.presentation.ui.theme.FontSize
import ru.vafeen.whisperoftasks.presentation.ui.theme.Theme
import java.time.LocalDate

@Composable
fun Reminder.ReminderDataString(
    context: Context,
    dateOfThisPage: LocalDate,
    modifier: Modifier = Modifier,
    viewModel: EventCreation,
    isItCandidateForDelete: Boolean?,
    changeStatusOfDeleting: (() -> Unit)?,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .alpha(if (isItCandidateForDelete == true) 0.5f else 1.0f),
        colors = CardDefaults.cardColors(containerColor = Theme.colors.buttonColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Column {
                Checkbox(
                    checked = dateOfDone != null && (dateOfDone!! >= dateOfThisPage ||
                            this@ReminderDataString.repeatDuration == RepeatDuration.NoRepeat),
                    onCheckedChange = if (isItCandidateForDelete != null && changeStatusOfDeleting != null) {
                        {
                            changeStatusOfDeleting()
                        }
                    } else {
                        {
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
                        }
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Theme.colors.oppositeTheme,
                        uncheckedColor = Theme.colors.oppositeTheme,
                        checkmarkColor = Theme.colors.singleTheme,
                    )
                )
            }
            Column(
                modifier = Modifier.weight(1f)
            ) {
                TextForThisTheme(text = title, fontSize = FontSize.big22, maxLines = 10)
                TextForThisTheme(text = text, fontSize = FontSize.medium19, maxLines = 10)

                TextForThisTheme(
                    text = stringResource(id = this@ReminderDataString.repeatDuration.resourceName),
                    fontSize = FontSize.medium19
                )
                if (this@ReminderDataString.isNotificationNeeded) {
                    Row {
                        TextForThisTheme(
                            text = "${this@ReminderDataString.dt.toLocalTime()}",
                            fontSize = FontSize.medium19
                        )
                        Icon(
                            painter = painterResource(R.drawable.message),
                            contentDescription = "message",
                            tint = Theme.colors.oppositeTheme
                        )
                    }
                }
            }
        }
    }
}