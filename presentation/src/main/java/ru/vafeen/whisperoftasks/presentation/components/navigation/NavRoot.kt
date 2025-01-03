package ru.vafeen.whisperoftasks.presentation.components.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.vafeen.whisperoftasks.presentation.components.main_screen.MainScreen
import ru.vafeen.whisperoftasks.presentation.components.reminders_screen.RemindersScreen
import ru.vafeen.whisperoftasks.presentation.components.settings_screen.SettingsScreen


@Composable
fun NavRoot() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Main) {
        composable<Screen.Main> { MainScreen() }
        composable<Screen.Reminders> { RemindersScreen() }
        composable<Screen.Settings> { SettingsScreen() }
    }
}