package ru.vafeen.whisperoftasks.ui.common.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import ru.vafeen.whisperoftasks.R
import ru.vafeen.whisperoftasks.noui.duration.RepeatDuration
import ru.vafeen.whisperoftasks.noui.local_database.entity.Reminder
import ru.vafeen.whisperoftasks.ui.common.components.bottom_bar.BottomBar
import ru.vafeen.whisperoftasks.ui.common.components.ui_utils.ReminderDataString
import ru.vafeen.whisperoftasks.ui.common.components.ui_utils.ReminderDialog
import ru.vafeen.whisperoftasks.ui.common.components.ui_utils.TextForThisTheme
import ru.vafeen.whisperoftasks.ui.common.navigation.ScreenRoute
import ru.vafeen.whisperoftasks.ui.common.viewmodel.RemindersScreenViewModel
import ru.vafeen.whisperoftasks.ui.theme.FontSize
import ru.vafeen.whisperoftasks.ui.theme.Theme
import java.time.LocalDate
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemindersScreen(
    viewModel: RemindersScreenViewModel,
    navController: NavController,
) {
    val context = LocalContext.current
    var reminders by remember {
        mutableStateOf(listOf<Reminder>())
    }
    var isAddingReminder by remember {
        mutableStateOf(false)
    }
    val lastReminder: MutableState<Reminder?> = remember {
        mutableStateOf(null)
    }
    LaunchedEffect(null) {
        viewModel.databaseRepository.getAllRemindersAsFlow().collect {
            reminders = it
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
                    .fillMaxWidth()
//                    .statusBarsPadding()
                ,
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
        BottomBar(containerColor = Theme.colors.mainColor,
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
        FloatingActionButton(
            onClick = {
                lastReminder.value = null
                isAddingReminder = true
            }, containerColor = Theme.colors.mainColor
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add new reminder",
                tint = Theme.colors.oppositeTheme
            )
        }
    }, floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        if (isAddingReminder) {
            if (lastReminder.value == null) {
                lastReminder.value = Reminder(
                    title = "",
                    text = "",
                    dt = LocalDateTime.now().plusMinutes(5),
                    idOfReminder = 0,
                    repeatDuration = RepeatDuration.NoRepeat
                )
            }

            ReminderDialog(
                newReminder = lastReminder as MutableState<Reminder>, // safety is above
                onDismissRequest = { isAddingReminder = false })
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Theme.colors.singleTheme),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val date by remember { mutableStateOf(LocalDate.now()) }
            if (reminders.isNotEmpty()) LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(reminders) {
                    it.ReminderDataString(
                        modifier = Modifier.clickable {
                            lastReminder.value = it
                            isAddingReminder = true
                        }, viewModel = viewModel, dateOfThisPage = date, context = context
                    )
                }
            }
            else TextForThisTheme(
                text = stringResource(id = R.string.you_havent_added_any_events_yet),
                fontSize = FontSize.big22,
            )
        }
    }
}