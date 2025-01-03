package ru.vafeen.whisperoftasks.presentation.common.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.vafeen.whisperoftasks.presentation.main_screen.MainScreen
import ru.vafeen.whisperoftasks.presentation.reminders_screen.RemindersScreen


@Composable
fun NavRoot() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen) {
        composable<Screen.MainScreen> { MainScreen() }
        composable<Screen.RemindersScreen> { RemindersScreen() }
    }
}