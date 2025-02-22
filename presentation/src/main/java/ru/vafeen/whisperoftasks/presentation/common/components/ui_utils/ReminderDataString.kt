package ru.vafeen.whisperoftasks.presentation.common.components.ui_utils


import android.widget.Toast
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.vafeen.whisperoftasks.domain.domain_models.Reminder
import ru.vafeen.whisperoftasks.domain.duration.RepeatDuration
import ru.vafeen.whisperoftasks.presentation.common.ReminderScheduler
import ru.vafeen.whisperoftasks.presentation.ui.theme.FontSize
import ru.vafeen.whisperoftasks.presentation.ui.theme.Theme
import ru.vafeen.whisperoftasks.presentation.utils.suitableColor
import ru.vafeen.whisperoftasks.resources.R
import java.time.LocalDate

@Composable
internal fun Reminder.ReminderDataString(
    mainModifier: Modifier = Modifier,
    mainColor: Color,
    dateOfThisPage: LocalDate? = null,
    modifier: Modifier = Modifier,
    viewModel: ReminderScheduler,
    isItCandidateForDelete: Boolean?,
    changeStatusOfDeleting: (() -> Unit)?,
) {
    val cor = rememberCoroutineScope()
    val context = LocalContext.current
    Card(
        border = if (isItCandidateForDelete == true)
            BorderStroke(width = 2.dp, color = Theme.colors.delete) else null,
        modifier = mainModifier
            .padding(10.dp)
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
                    enabled = changeStatusOfDeleting == null,
                    checked = dateOfDone != null && dateOfThisPage != null && (dateOfDone!! >= dateOfThisPage ||
                            this@ReminderDataString.repeatDuration == RepeatDuration.NoRepeat),
                    onCheckedChange = {
                        cor.launch(Dispatchers.IO) {
                            viewModel.setEvent(
                                copy(
                                    dateOfDone = if (this@ReminderDataString.repeatDuration != RepeatDuration.NoRepeat) {
                                        if (dateOfDone == dateOfThisPage) null else {
                                            if (dateOfDone != null)
                                                withContext(Dispatchers.Main) {
                                                    Toast.makeText(
                                                        context,
                                                        "Помечено выполненным до сегодня",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
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
                        checkedColor = mainColor,
                        uncheckedColor = mainColor,
                        checkmarkColor = mainColor.suitableColor(),
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
                    fontSize = FontSize.medium19,
                    maxLines = 10
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