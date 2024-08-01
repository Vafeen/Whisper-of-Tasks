package ru.vafeen.reminder.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.vafeen.reminder.ui.common.navigation.ScreenRoute
import ru.vafeen.reminder.ui.common.screen.MainScreen
import ru.vafeen.reminder.ui.common.screen.RemindersScreen
import ru.vafeen.reminder.ui.common.viewmodel.factory.MainScreenViewModelFactory
import ru.vafeen.reminder.ui.common.viewmodel.factory.ReminderScreenViewModelFactory
import ru.vafeen.reminder.ui.common.viewmodel.factory.provider.ViewModelsFactoryProvider
import ru.vafeen.reminder.ui.theme.ReminderTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var viewModelsFactoryProvider: ViewModelsFactoryProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReminderTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = ScreenRoute.Main.route) {
                    composable(route = ScreenRoute.Main.route) {
                        MainScreen(
                            viewModel = viewModel(factory = viewModelsFactoryProvider.mainScreenViewModelFactory),
                            navController = navController
                        )
                    }
                    composable(route = ScreenRoute.Reminders.route) {
                        RemindersScreen(
                            viewModel = viewModel(factory = viewModelsFactoryProvider.reminderScreenViewModelFactory),
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}

