package ru.vafeen.whisperoftasks.presentation.components.trash_bin_screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import org.koin.androidx.compose.koinViewModel
import ru.vafeen.whisperoftasks.domain.domain_models.Reminder
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.TODOWithReminders
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.ReminderDataString
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.TextForThisTheme
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.customMainColorOrDefault
import ru.vafeen.whisperoftasks.presentation.components.navigation.BottomBarNavigator
import ru.vafeen.whisperoftasks.presentation.components.reminder_dialog.ReminderDialog
import ru.vafeen.whisperoftasks.presentation.ui.theme.FontSize
import ru.vafeen.whisperoftasks.presentation.ui.theme.Theme
import ru.vafeen.whisperoftasks.presentation.utils.suitableColor
import ru.vafeen.whisperoftasks.resources.R

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
internal fun TrashBinScreen(bottomBarNavigator: BottomBarNavigator) {
    val viewModel: TrashBinScreenViewModel = koinViewModel()
    val cor = rememberCoroutineScope()

    val reminders by viewModel.remindersFlow.collectAsState(listOf())
    val isSelectingProcess by remember {
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
                if (!isSelectingProcess) {
                    lastReminder.value = reminder
                    isEditingReminder = true
                } else {
                    viewModel.changeStatusForDeleting(reminder)
                }
            },
            onLongClick = {
                if (!isSelectingProcess) {
                    viewModel.changeStatusForDeleting(reminder)
                } else {
                    viewModel.clearSelectedReminders()
                }
            }
        )


    BackHandler {
        when {
            isSelectingProcess -> {
                viewModel.clearSelectedReminders()
            }

            else -> {
                bottomBarNavigator.back()
            }
        }
    }
    Scaffold(
        containerColor = Theme.colors.background, modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(settings.customMainColorOrDefault()),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { bottomBarNavigator.back() }) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_back),
                        tint = settings.customMainColorOrDefault().suitableColor(),
                        contentDescription = stringResource(R.string.go_back)
                    )
                }
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = stringResource(R.string.trash_bin), fontSize = FontSize.big22,
                    color = settings.customMainColorOrDefault().suitableColor()
                )
            }

        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (isAddingReminder || isEditingReminder) {
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
                    LazyColumn {
                        items(items = reminders) {
                            it.ReminderDataString(
                                mainColor = settings.customMainColorOrDefault(),
                                modifier = Modifier.combinedClickableForRemovingReminder(
                                    reminder = it
                                ),
                                isItCandidateForDelete = viewModel.selectedReminders.contains(
                                    it.idOfReminder
                                ),
                                changeStatusOfSelecting = if (isSelectingProcess) {
                                    { viewModel.changeStatusForDeleting(it) }
                                } else null,
                            )
                        }
                    }
                } else TextForThisTheme(
                    text = stringResource(id = R.string.you_havent_added_any_events_yet),
                    fontSize = FontSize.big22,
                    maxLines = 10,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            if (isSelectingProcess) TODOWithReminders(
                actionName = R.string.restore_reminders,
                actionColor = Theme.colors.restore,
                actionTextColor = Color.Black,
            ) {
                viewModel.restoreSelectedReminders()
            }
            if (isSelectingProcess) TODOWithReminders(
                actionName = R.string.delete_selected,
                actionColor = Theme.colors.delete,
                actionTextColor = Color.Black,
            ) {
                viewModel.deleteForeverSelectedReminder()
            }
        }

    }
}