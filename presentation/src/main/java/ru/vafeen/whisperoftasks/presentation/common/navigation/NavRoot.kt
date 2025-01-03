package ru.vafeen.whisperoftasks.presentation.common.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.vafeen.whisperoftasks.presentation.main_screen.MainScreen
import ru.vafeen.whisperoftasks.presentation.reminders_screen.RemindersScreen
import ru.vafeen.whisperoftasks.presentation.settings_screen.SettingsScreen


@Composable
fun NavRoot() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Main) {
        composable<Screen.Main> { MainScreen() }
        composable<Screen.Reminders> { RemindersScreen() }
        composable<Screen.Settings> { SettingsScreen() }
    }
}