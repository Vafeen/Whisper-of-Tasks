package ru.vafeen.whisperoftasks.presentation.components.reminders_screen

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import org.koin.androidx.compose.koinViewModel
import ru.vafeen.whisperoftasks.domain.domain_models.Reminder
import ru.vafeen.whisperoftasks.domain.duration.RepeatDuration
import ru.vafeen.whisperoftasks.domain.utils.nullTime
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.DeleteReminders
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.ReminderCard
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.TextForThisTheme
import ru.vafeen.whisperoftasks.presentation.components.navigation.BottomBarNavigator
import ru.vafeen.whisperoftasks.presentation.components.reminder_dialog.ReminderDialog
import ru.vafeen.whisperoftasks.presentation.ui.theme.FontSize
import ru.vafeen.whisperoftasks.presentation.ui.theme.Theme
import ru.vafeen.whisperoftasks.resources.R
import java.time.LocalDate
import java.time.LocalDateTime


@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun RemindersScreen(bottomBarNavigator: BottomBarNavigator) {
    val viewModel: RemindersScreenViewModel = koinViewModel()
    val reminders by viewModel.remindersFlow.collectAsState()
    val remindersForDeleting by viewModel.remindersForDeletingFlow.collectAsState()
    val isDeletingInProcess by remember {
        derivedStateOf {
            remindersForDeleting.isNotEmpty()
        }
    }


    var isAddingReminder by remember {
        mutableStateOf(false)
    }
    var isEditingReminder by remember {
        mutableStateOf(false)
    }
    val lastReminder: MutableState<Reminder?> = remember {
        mutableStateOf(null)
    }
    if (isAddingReminder || isEditingReminder) {
        if (isAddingReminder) {
            lastReminder.value = Reminder(
                title = "",
                text = "",
                dt = LocalDateTime.of(LocalDate.now(), nullTime),
                idOfReminder = 0,
                repeatDuration = RepeatDuration.NoRepeat
            )
        }
        ReminderDialog(
            newReminder = lastReminder as MutableState<Reminder>, // safety is above
            onDismissRequest = {
                isAddingReminder = false
                isEditingReminder = false
            }
        )

    }

    fun Modifier.combinedClickableForRemovingReminder(reminder: Reminder): Modifier =
        this.combinedClickable(
            onClick = {
                if (!isDeletingInProcess) {
                    lastReminder.value = reminder
                    isEditingReminder = true
                } else {
                    viewModel.changeStatusForDeleting(reminder)
                }
            },
            onLongClick = {
                if (!isDeletingInProcess) {
                    viewModel.setReminderAsCandidateForDeleting(reminder)
                } else {
                    viewModel.clearRemindersForDeleting()
                }
            }
        )



    BackHandler {
        when {
            isDeletingInProcess -> {
                viewModel.clearRemindersForDeleting()
            }

            else -> {
                bottomBarNavigator.back()
            }
        }
    }
    Scaffold(
        containerColor = Theme.colors.background, modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { isAddingReminder = true },
                containerColor = Theme.colors.mainColor
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(ru.vafeen.whisperoftasks.resources.R.string.add_reminder)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            if (reminders.isNotEmpty()) {
                LazyVerticalGrid(
                    modifier = Modifier.weight(1f),
                    columns = GridCells.Fixed(2)
                ) {
                    items(items = reminders) {
                        it.ReminderCard(
                            modifier = Modifier.combinedClickableForRemovingReminder(reminder = it),
                            isItCandidateForDelete = remindersForDeleting.contains(it.idOfReminder),
                        )
                    }
                }
            } else TextForThisTheme(
                text = stringResource(id = R.string.you_havent_added_any_events_yet),
                fontSize = FontSize.big22,
            )
            if (isDeletingInProcess) DeleteReminders {
                viewModel.removeEventsForReminderForDeleting()
                viewModel.clearRemindersForDeleting()
            }
        }
    }
}