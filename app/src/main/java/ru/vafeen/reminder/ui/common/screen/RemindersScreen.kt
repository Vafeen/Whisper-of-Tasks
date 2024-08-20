package ru.vafeen.reminder.ui.common.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.vafeen.reminder.noui.local_database.entity.Reminder
import ru.vafeen.reminder.ui.common.components.AddReminderDialog
import ru.vafeen.reminder.ui.common.components.BottomBar
import ru.vafeen.reminder.ui.common.components.ReminderDataString
import ru.vafeen.reminder.ui.common.navigation.ScreenRoute
import ru.vafeen.reminder.ui.common.viewmodel.RemindersScreenViewModel
import ru.vafeen.reminder.ui.theme.Theme

@SuppressLint("CoroutineCreationDuringComposition")
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
    LaunchedEffect(null) {
        Log.d("cor", "launch")
        viewModel.databaseRepository.getAllReminders().collect {
            reminders = it
        }
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
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
                onClick = { isAddingReminder = true },
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
            AddReminderDialog(addReminder = {
                cor.launch(Dispatchers.IO) {
                    viewModel.databaseRepository.insertReminder(it)
                }
            }, onDismissRequest = { isAddingReminder = false })
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.colors.singleTheme)
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(reminders) { it.ReminderDataString(viewModel = viewModel) }
            }
        }
    }
}