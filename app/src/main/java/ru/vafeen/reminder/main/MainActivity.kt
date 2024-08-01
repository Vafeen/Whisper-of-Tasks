package ru.vafeen.reminder.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.vafeen.reminder.ui.common.navigation.ScreenRoute
import ru.vafeen.reminder.ui.common.screen.MainScreen
import ru.vafeen.reminder.ui.common.screen.RemindersScreen
import ru.vafeen.reminder.ui.common.viewmodel.MainScreenViewModel
import ru.vafeen.reminder.ui.common.viewmodel.RemindersScreenViewModel
import ru.vafeen.reminder.ui.theme.ReminderTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainScreenViewModel by viewModels<MainScreenViewModel>()
    private val remindersScreenViewModel by viewModels<RemindersScreenViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReminderTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = ScreenRoute.Main.route) {
                    composable(route = ScreenRoute.Main.route) {
                        MainScreen(viewModel = mainScreenViewModel)
                    }
                    composable(route = ScreenRoute.Reminders.route) {
                        RemindersScreen(viewModel = remindersScreenViewModel)
                    }
                }
            }
        }
    }
}

