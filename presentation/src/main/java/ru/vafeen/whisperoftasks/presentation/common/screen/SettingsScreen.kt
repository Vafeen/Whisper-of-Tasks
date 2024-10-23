package ru.vafeen.whisperoftasks.presentation.common.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import ru.vafeen.whisperoftasks.presentation.common.components.bottom_bar.BottomBar
import ru.vafeen.whisperoftasks.presentation.common.navigation.ScreenRoute
import ru.vafeen.whisperoftasks.presentation.common.viewmodel.SettingsScreenViewModel
import ru.vafeen.whisperoftasks.presentation.ui.theme.Theme

@Composable
fun SettingsScreen(
    viewModel: SettingsScreenViewModel,
    navController: NavController,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            BottomBar(
                containerColor = Theme.colors.mainColor,
                selectedSettingsScreen = true,
                navigateToRemindersScreen = {
                    navController.popBackStack()
                    navController.navigate(ScreenRoute.Reminders.route)
                },
                navigateToMainScreen = {
                    navController.popBackStack()
                    navController.popBackStack()
                    navController.navigate(ScreenRoute.Main.route)
                })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.colors.singleTheme)
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "settings",
                color = Theme.colors.oppositeTheme
            )
        }
    }
}