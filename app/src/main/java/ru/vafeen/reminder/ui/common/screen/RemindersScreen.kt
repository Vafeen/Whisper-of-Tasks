package ru.vafeen.reminder.ui.common.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import ru.vafeen.reminder.ui.common.components.BottomBar
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
    cor.launch(Dispatchers.Main) {
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
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.colors.singleTheme)
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            for (reminder in reminders) {
                Log.d("reminder", "$reminder")
                Text(text = reminder.toString())
            }
            Button(onClick = {
                cor.launch(Dispatchers.IO) {
                    val newRem = Reminder(
                        id = 0,
                        title = "title",
                        text = "text"
                    )
                    viewModel.databaseRepository.insertAllReminders(newRem)
                }
            }) {
                Text(text = "add")
            }
        }
    }
}