package ru.vafeen.reminder.ui.common.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import androidx.navigation.NavController
import ru.vafeen.reminder.noui.local_database.entity.Reminder
import ru.vafeen.reminder.ui.common.components.bottom_bar.BottomBar
import ru.vafeen.reminder.ui.common.components.ui_utils.ReminderDataString
import ru.vafeen.reminder.ui.common.components.ui_utils.ReminderDialog
import ru.vafeen.reminder.ui.common.components.ui_utils.TextForThisTheme
import ru.vafeen.reminder.ui.common.navigation.ScreenRoute
import ru.vafeen.reminder.ui.common.viewmodel.RemindersScreenViewModel
import ru.vafeen.reminder.ui.theme.FontSize
import ru.vafeen.reminder.ui.theme.Theme


@Composable
fun RemindersScreen(
    viewModel: RemindersScreenViewModel,
    navController: NavController
) {
    val cor = rememberCoroutineScope()
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
        Log.d("cor", "launch")
        viewModel.databaseRepository.getAllRemindersAsFlow().collect {
            reminders = it
        }
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding(),
                horizontalArrangement = Arrangement.Center
            ) {
                TextForThisTheme(text = "Reminders", fontSize = FontSize.huge27)
            }
        },
        bottomBar = {
            BottomBar(
                containerColor = Theme.colors.mainColor,
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
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    lastReminder.value = null
                    isAddingReminder = true
                },
                containerColor = Theme.colors.mainColor
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add new reminder",
                    tint = Theme.colors.oppositeTheme
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        if (isAddingReminder)
            ReminderDialog(newReminder = lastReminder,
                onDismissRequest = { isAddingReminder = false })
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.colors.singleTheme)
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (reminders.isNotEmpty())
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(reminders) {
                        it.ReminderDataString(
                            modifier = Modifier.clickable {
                                lastReminder.value = it
                                isAddingReminder = true
                            },
                            viewModel = viewModel
                        )
                    }
                }
            else TextForThisTheme(
                text = "Вы еще не добавили\nни одного напоминания"
            )
        }
    }
}