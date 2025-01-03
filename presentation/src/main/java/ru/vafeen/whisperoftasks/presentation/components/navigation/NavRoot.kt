package ru.vafeen.whisperoftasks.presentation.components.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.vafeen.whisperoftasks.presentation.common.components.bottom_bar.BottomBar
import ru.vafeen.whisperoftasks.presentation.components.main_screen.MainScreen
import ru.vafeen.whisperoftasks.presentation.components.reminders_screen.RemindersScreen
import ru.vafeen.whisperoftasks.presentation.components.settings_screen.SettingsScreen


@Composable
internal fun NavRoot(bottomBarNavigator: BottomBarNavigator) {
    bottomBarNavigator.navController = rememberNavController()
    Scaffold(
        bottomBar = {
            val selectedScreen by bottomBarNavigator.currentScreen.collectAsState()
            BottomBar(
                selectedScreen = selectedScreen,
                bottomBarNavigator = bottomBarNavigator,
                containerColor = Color.Blue
            )
        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = bottomBarNavigator.navController!!, startDestination = Screen.Main
        ) {
            composable<Screen.Main> { MainScreen() }
            composable<Screen.Reminders> { RemindersScreen() }
            composable<Screen.Settings> { SettingsScreen() }
        }
    }
}