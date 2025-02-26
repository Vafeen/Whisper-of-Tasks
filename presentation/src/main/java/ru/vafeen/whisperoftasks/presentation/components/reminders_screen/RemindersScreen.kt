package ru.vafeen.whisperoftasks.presentation.components.reminders_screen

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import ru.vafeen.whisperoftasks.domain.domain_models.Reminder
import ru.vafeen.whisperoftasks.domain.duration.RepeatDuration
import ru.vafeen.whisperoftasks.domain.utils.generateID
import ru.vafeen.whisperoftasks.domain.utils.nullTime
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.TODOWithReminders
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.ListGridChangeView
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.ReminderDataString
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.TextForThisTheme
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.customMainColorOrDefault
import ru.vafeen.whisperoftasks.presentation.components.navigation.BottomBarNavigator
import ru.vafeen.whisperoftasks.presentation.components.reminder_dialog.ReminderDialog
import ru.vafeen.whisperoftasks.presentation.ui.theme.FontSize
import ru.vafeen.whisperoftasks.presentation.ui.theme.Theme
import ru.vafeen.whisperoftasks.presentation.utils.suitableColor
import ru.vafeen.whisperoftasks.resources.R
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.math.roundToInt


@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun RemindersScreen(bottomBarNavigator: BottomBarNavigator) {
    val cor = rememberCoroutineScope()
    val dateToday by remember {
        mutableStateOf(LocalDate.now())
    }
    val viewModel: RemindersScreenViewModel = koinViewModel()
    val reminders by viewModel.remindersFlow.collectAsState(listOf())
    val isDeletingInProcess by remember {
        derivedStateOf {
            viewModel.selectedReminders.isNotEmpty()
        }
    }
    val settings by viewModel.settings.collectAsState()
    val mainColor = settings.customMainColorOrDefault()
    var isAddingReminder by remember {
        mutableStateOf(false)
    }
    var isEditingReminder by remember {
        mutableStateOf(false)
    }
    val lastReminder: MutableState<Reminder?> = remember {
        mutableStateOf(null)
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
                    viewModel.changeStatusForDeleting(reminder)
                } else {
                    viewModel.clearSelectedReminders()
                }
            }
        )

    var fabState by remember { mutableStateOf(IntOffset(0, 0)) }

    BackHandler {
        when {
            isDeletingInProcess -> {
                viewModel.clearSelectedReminders()
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
                modifier = Modifier
                    .offset {
                        fabState
                    }
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDrag = { change, dragAmount ->
                                fabState = fabState.copy(
                                    x = fabState.x + dragAmount.x.roundToInt(),
                                    y = fabState.y + dragAmount.y.roundToInt()
                                )
                            })
                    },
                onClick = { isAddingReminder = true },
                containerColor = mainColor,
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription =
                    stringResource(R.string.add_reminder),
                    tint =
                    mainColor.suitableColor(),
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            ListGridChangeView(
                isListChosen = settings.isListChosen,
                changeToList = {
                    viewModel.saveSettings {
                        it.copy(isListChosen = true)
                    }
                },
                changeToGrid = {
                    viewModel.saveSettings {
                        it.copy(isListChosen = false)
                    }
                })
            if (isAddingReminder || isEditingReminder) {
                if (isAddingReminder) {
                    lastReminder.value = Reminder(
                        title = "",
                        text = "",
                        dt = LocalDateTime.of(LocalDate.now(), nullTime),
                        idOfReminder = reminders.map { it.idOfReminder }
                            .generateID(),
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
            Column(modifier = Modifier.weight(1f)) {
                if (reminders.isNotEmpty()) {
                    if (settings.isListChosen) {
                        LazyColumn {
                            items(items = reminders) {
                                it.ReminderDataString(
                                    mainColor = settings.customMainColorOrDefault(),
                                    modifier = Modifier.combinedClickableForRemovingReminder(
                                        reminder = it
                                    ),
                                    setEvent = viewModel::setEvent,
                                    dateOfThisPage = dateToday,
                                    isItCandidateForDelete = viewModel.selectedReminders.contains(
                                        it.idOfReminder
                                    ),
                                    changeStatusOfSelecting = if (isDeletingInProcess) {
                                        { viewModel.changeStatusForDeleting(it) }
                                    } else null,
                                    showNotification = viewModel::showNotification
                                )
                            }
                        }
                    } else {
                        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                            items(items = reminders) {
                                it.ReminderDataString(
                                    mainColor = settings.customMainColorOrDefault(),
                                    modifier = Modifier.combinedClickableForRemovingReminder(
                                        reminder = it
                                    ),
                                    setEvent = viewModel::setEvent,
                                    dateOfThisPage = dateToday,
                                    isItCandidateForDelete = viewModel.selectedReminders.contains(
                                        it.idOfReminder
                                    ),
                                    changeStatusOfSelecting = if (isDeletingInProcess) {
                                        { viewModel.changeStatusForDeleting(it) }
                                    } else null,
                                    showNotification = viewModel::showNotification
                                )
                            }
                        }
                    }
                } else TextForThisTheme(
                    text = stringResource(id = R.string.you_havent_added_any_events_yet),
                    fontSize = FontSize.big22,
                    maxLines = 10,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            if (isDeletingInProcess) TODOWithReminders(
                actionName = R.string.mv_to_trash_selected,
                actionColor = Theme.colors.delete,
                actionTextColor = Color.Black,
            ) {
                viewModel.moveToTrashSelectedReminders()
            }
        }
    }
}