package ru.vafeen.whisperoftasks.main

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.vafeen.whisperoftasks.noui.permission.RequestNotificationPermission
import ru.vafeen.whisperoftasks.ui.common.components.ui_utils.CheckUpdateAndOpenBottomSheetIfNeed
import ru.vafeen.whisperoftasks.ui.common.components.ui_utils.SetNavigationBarColor
import ru.vafeen.whisperoftasks.ui.common.navigation.ScreenRoute
import ru.vafeen.whisperoftasks.ui.common.screen.MainScreen
import ru.vafeen.whisperoftasks.ui.common.screen.RemindersScreen
import ru.vafeen.whisperoftasks.ui.common.screen.SettingsScreen
import ru.vafeen.whisperoftasks.ui.common.viewmodel.MainActivityViewModel
import ru.vafeen.whisperoftasks.ui.common.viewmodel.MainScreenViewModel
import ru.vafeen.whisperoftasks.ui.common.viewmodel.RemindersScreenViewModel
import ru.vafeen.whisperoftasks.ui.common.viewmodel.SettingsScreenViewModel
import ru.vafeen.whisperoftasks.ui.theme.MainTheme
import ru.vafeen.whisperoftasks.ui.theme.Theme
import ru.vafeen.whisperoftasks.utils.copyToClipboard

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModel()
    private val mainScreenViewModel: MainScreenViewModel by viewModel()
    private val remindersScreenViewModel: RemindersScreenViewModel by viewModel()
    private val settingsScreenViewModel: SettingsScreenViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            // Копирование информации об ошибке в буфер обмена
            copyToClipboard(throwable)

            // Выводим информацию в Logcat для отладки
            Log.e("UncaughtException", "Uncaught exception in thread ${thread.name}", throwable)
        }
        enableEdgeToEdge()
        setContent {
            MainTheme {
                SetNavigationBarColor(color = Theme.colors.singleTheme)
                RequestNotificationPermission()
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

