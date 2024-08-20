package ru.vafeen.reminder.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.vafeen.reminder.ui.common.navigation.ScreenRoute
import ru.vafeen.reminder.ui.common.screen.MainScreen
import ru.vafeen.reminder.ui.common.screen.RemindersScreen
import ru.vafeen.reminder.ui.common.screen.SettingsScreen
import ru.vafeen.reminder.ui.common.viewmodel.MainActivityViewModel
import ru.vafeen.reminder.ui.common.viewmodel.factory.MainActivityViewModelFactory
import ru.vafeen.reminder.ui.theme.MainTheme
import ru.vafeen.reminder.ui.theme.Theme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var viewModelFactory: MainActivityViewModelFactory
    private val viewModel by viewModels<MainActivityViewModel>(
        factoryProducer = { viewModelFactory }
    )

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = ScreenRoute.Main.route,
                    modifier = Modifier.background(Theme.colors.singleTheme)
                ) {
                    composable(route = ScreenRoute.Main.route) {
                        MainScreen(
                            viewModel = viewModel(factory = viewModel.mainScreenViewModelFactory),
                            navController = navController
                        )
                    }
                    composable(route = ScreenRoute.Reminders.route) {
                        RemindersScreen(
                            viewModel = viewModel(factory = viewModel.reminderScreenViewModelFactory),
                            navController = navController
                        )
                    }
                    composable(route = ScreenRoute.Settings.route) {
                        SettingsScreen(
                            viewModel = viewModel(factory = viewModel.settingsScreenViewModelFactory),
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}

