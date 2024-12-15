package ru.vafeen.whisperoftasks.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.CheckUpdateAndOpenBottomSheetIfNeed
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.SetNavigationBarColor
import ru.vafeen.whisperoftasks.presentation.common.navigation.ScreenRoute
import ru.vafeen.whisperoftasks.presentation.common.screen.MainScreen
import ru.vafeen.whisperoftasks.presentation.common.screen.RemindersScreen
import ru.vafeen.whisperoftasks.presentation.common.screen.SettingsScreen
import ru.vafeen.whisperoftasks.presentation.common.viewmodel.MainActivityViewModel
import ru.vafeen.whisperoftasks.presentation.permission.RequestNotificationPermission
import ru.vafeen.whisperoftasks.presentation.ui.theme.MainTheme
import ru.vafeen.whisperoftasks.presentation.ui.theme.Theme

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.registerGeneralExceptionCallback(context = this)
        enableEdgeToEdge()
        setContent {
            MainTheme {
                SetNavigationBarColor(color = Theme.colors.singleTheme)
                RequestNotificationPermission()
                if (!viewModel.updateIsShowed) {
                    CheckUpdateAndOpenBottomSheetIfNeed {
                        viewModel.updateIsShowed = true
                    }
                }

                val navController = rememberNavController()
                Column(
                    modifier = Modifier
                        .background(Theme.colors.singleTheme)
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = ScreenRoute.Main.route,
                    ) {
                        composable(route = ScreenRoute.Main.route) {
                            MainScreen(navController = navController)
                        }
                        composable(route = ScreenRoute.Reminders.route) {
                            RemindersScreen(navController = navController)
                        }
                        composable(route = ScreenRoute.Settings.route) {
                            SettingsScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}

