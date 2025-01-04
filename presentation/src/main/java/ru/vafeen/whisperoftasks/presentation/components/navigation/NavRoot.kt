package ru.vafeen.whisperoftasks.presentation.components.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
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
import ru.vafeen.whisperoftasks.presentation.ui.theme.Theme


@Composable
internal fun NavRoot(bottomBarNavigator: BottomBarNavigator) {
    bottomBarNavigator.navController = rememberNavController()
    Scaffold(
        containerColor = Theme.colors.background,
        bottomBar = {
            val selectedScreen by bottomBarNavigator.currentScreen.collectAsState()
            BottomBar(
                selectedScreen = selectedScreen,
                bottomBarNavigator = bottomBarNavigator,
                containerColor = Color.Blue
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            NavHost(
                navController = bottomBarNavigator.navController!!, startDestination = Screen.Main,
                enterTransition = { fadeIn(animationSpec = tween()) },
                exitTransition = { fadeOut(animationSpec = tween()) },
                popEnterTransition = { fadeIn(animationSpec = tween()) },
                popExitTransition = { fadeOut(animationSpec = tween()) },
            ) {
                composable<Screen.Main> { MainScreen() }
                composable<Screen.Reminders> { RemindersScreen() }
                composable<Screen.Settings> { SettingsScreen() }
            }
        }
    }
}