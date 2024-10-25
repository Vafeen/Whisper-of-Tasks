package ru.vafeen.whisperoftasks.presentation.common.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel
import ru.vafeen.whisperoftasks.data.R
import ru.vafeen.whisperoftasks.data.local_database.entity.Reminder
import ru.vafeen.whisperoftasks.domain.utils.getMainColorForThisTheme
import ru.vafeen.whisperoftasks.presentation.common.components.bottom_bar.BottomBar
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.DeleteReminders
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.ReminderDataString
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.ReminderDialog
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.TextForThisTheme
import ru.vafeen.whisperoftasks.presentation.common.navigation.ScreenRoute
import ru.vafeen.whisperoftasks.presentation.common.viewmodel.RemindersScreenViewModel
import ru.vafeen.whisperoftasks.presentation.ui.theme.FontSize
import ru.vafeen.whisperoftasks.presentation.ui.theme.Theme
import ru.vafeen.whisperoftasks.presentation.utils.suitableColor
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun RemindersScreen(navController: NavController) {
    val dark = isSystemInDarkTheme()
    val viewModel: RemindersScreenViewModel = koinViewModel()
    val context = LocalContext.current
    var reminders by remember {
        mutableStateOf(listOf<Reminder>())
    }
    val nullTime = LocalTime.of(0, 0)
    var isAddingReminder by remember {
        mutableStateOf(false)
    }
    val lastReminder: MutableState<Reminder?> = remember {
        mutableStateOf(null)
    }
    val settings by viewModel.settings.collectAsState()
    val mainColor = settings.getMainColorForThisTheme(isDark = dark) ?: Theme.colors.mainColor
    var fabOffset by remember { mutableStateOf(Offset(0f, 0f)) }
    LaunchedEffect(null) {
        viewModel.databaseRepository.getAllRemindersAsFlow().collect {
            reminders = it
        }
    }
    var isDeletingInProcess by remember { mutableStateOf(false) }
    val reminderForRemoving = remember { mutableStateMapOf<Int, Reminder>() }
    fun Modifier.combinedClickableForRemovingReminder(reminder: Reminder): Modifier =
        this.combinedClickable(
            onClick = {
                if (!isDeletingInProcess) {
                    lastReminder.value = reminder
                    isAddingReminder = true
                } else {
                    if (reminderForRemoving[reminder.idOfReminder] == null)
                        reminderForRemoving[reminder.idOfReminder] = reminder
                    else {
                        reminderForRemoving.remove(reminder.idOfReminder)
                        if (reminderForRemoving.isEmpty())
                            isDeletingInProcess = false
                    }
                }
            },
            onLongClick = {
                isDeletingInProcess = !isDeletingInProcess
                if (isDeletingInProcess)
                    reminderForRemoving[reminder.idOfReminder] = reminder
                else
                    reminderForRemoving.clear()
            }
        )

    fun Reminder.isItCandidateForDelete(): Boolean? =
        if (isDeletingInProcess) reminderForRemoving[idOfReminder] != null else null

    fun Reminder.changeStatusOfDeleting() {
        isItCandidateForDelete()?.let {
            if (it)
                reminderForRemoving.remove(idOfReminder)
            else reminderForRemoving.set(idOfReminder, this)
        }
    }
    BackHandler {
        when {
            isDeletingInProcess -> {
                isDeletingInProcess = false
                reminderForRemoving.clear()
            }

            else -> {
                navController.popBackStack()
            }
        }
    }
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(colors = TopAppBarColors(
            containerColor = Theme.colors.singleTheme,
            scrolledContainerColor = Theme.colors.singleTheme,
            navigationIconContentColor = Theme.colors.oppositeTheme,
            titleContentColor = Theme.colors.oppositeTheme,
            actionIconContentColor = Theme.colors.singleTheme
        ), title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextForThisTheme(
                    text = stringResource(id = R.string.tasks_list),
                    fontSize = FontSize.huge27
                )
            }
        })

    }, bottomBar = {
        BottomBar(containerColor = mainColor,
            selectedRemindersScreen = true,
            navigateToMainScreen = {
                navController.popBackStack()
                navController.popBackStack()
                navController.navigate(ScreenRoute.Main.route)
            },
            navigateToSettingsScreen = {
                navController.popBackStack()
                navController.navigate(ScreenRoute.Settings.route)
            })
    }, floatingActionButton = {
        if (!isDeletingInProcess)
            FloatingActionButton(
                modifier = Modifier
                    .offset { IntOffset(fabOffset.x.toInt(), fabOffset.y.toInt()) }
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            fabOffset = Offset(
                                x = fabOffset.x + dragAmount.x,
                                y = fabOffset.y + dragAmount.y
                            )
                        }
                    },
                onClick = {
                    lastReminder.value = null
                    isAddingReminder = true
                }, containerColor = mainColor
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add new reminder",
                    tint = mainColor.suitableColor()
                )
            }
    }, floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        if (isAddingReminder) {
            if (lastReminder.value == null) {
                lastReminder.value = Reminder(
                    title = "",
                    text = "",
                    dt = LocalDateTime.of(LocalDate.now(), nullTime),
                    idOfReminder = 0,
                    repeatDuration = ru.vafeen.whisperoftasks.data.duration.RepeatDuration.NoRepeat
                )
            }

            ReminderDialog(
                newReminder = lastReminder as MutableState<Reminder>, // safety is above
                onDismissRequest = { isAddingReminder = false },
                eventCreation = viewModel
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Theme.colors.singleTheme),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val date by remember { mutableStateOf(LocalDate.now()) }
            if (reminders.isNotEmpty()) LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                items(reminders) {
                    it.ReminderDataString(
                        modifier = Modifier.combinedClickableForRemovingReminder(reminder = it),
                        viewModel = viewModel,
                        dateOfThisPage = date,
                        context = context,
                        isItCandidateForDelete = it.isItCandidateForDelete(),
                        changeStatusOfDeleting = it::changeStatusOfDeleting,
                    )
                }
            }
            else TextForThisTheme(
                text = stringResource(id = R.string.you_havent_added_any_events_yet),
                fontSize = FontSize.big22,
            )
            if (isDeletingInProcess) DeleteReminders {
                isDeletingInProcess = false
                reminderForRemoving.values.forEach {
                    viewModel.removeEvent(it)
                    reminderForRemoving.remove(it.idOfReminder)
                }
            }
        }
    }
}