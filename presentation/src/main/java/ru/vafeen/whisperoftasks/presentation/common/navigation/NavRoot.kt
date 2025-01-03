package ru.vafeen.whisperoftasks.presentation.common.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun NavRoot() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen) {
        composable<Screen.MainScreen> { }
    }
}