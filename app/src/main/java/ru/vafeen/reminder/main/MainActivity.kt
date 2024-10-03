package ru.vafeen.reminder.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.vafeen.reminder.noui.permission.RequestNotificationPermission
import ru.vafeen.reminder.ui.common.components.ui_utils.CheckUpdateAndOpenBottomSheetIfNeed
import ru.vafeen.reminder.ui.common.navigation.ScreenRoute
import ru.vafeen.reminder.ui.common.screen.MainScreen
import ru.vafeen.reminder.ui.common.screen.RemindersScreen
import ru.vafeen.reminder.ui.common.screen.SettingsScreen
import ru.vafeen.reminder.ui.common.viewmodel.MainActivityViewModel
import ru.vafeen.reminder.ui.common.viewmodel.MainScreenViewModel
import ru.vafeen.reminder.ui.common.viewmodel.RemindersScreenViewModel
import ru.vafeen.reminder.ui.common.viewmodel.SettingsScreenViewModel
import ru.vafeen.reminder.ui.theme.MainTheme
import ru.vafeen.reminder.ui.theme.Theme


class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModel()
    private val mainScreenViewModel: MainScreenViewModel by viewModel()
    private val remindersScreenViewModel: RemindersScreenViewModel by viewModel()
    private val settingsScreenViewModel: SettingsScreenViewModel by viewModel()

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainTheme {
                RequestNotificationPermission(context = LocalContext.current)
                if (!viewModel.updateIsShowed) {
                    CheckUpdateAndOpenBottomSheetIfNeed(
                        networkRepository = viewModel.networkRepository
                    ) {
                        viewModel.updateIsShowed = true
                    }
                }

                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = ScreenRoute.Main.route,
                    modifier = Modifier.background(Theme.colors.singleTheme)
                ) {
                    composable(route = ScreenRoute.Main.route) {
                        MainScreen(
                            viewModel = mainScreenViewModel,
                            navController = navController
                        )
                    }
                    composable(route = ScreenRoute.Reminders.route) {
                        RemindersScreen(
                            viewModel = remindersScreenViewModel,
                            navController = navController
                        )
                    }
                    composable(route = ScreenRoute.Settings.route) {
                        SettingsScreen(
                            viewModel = settingsScreenViewModel,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}

