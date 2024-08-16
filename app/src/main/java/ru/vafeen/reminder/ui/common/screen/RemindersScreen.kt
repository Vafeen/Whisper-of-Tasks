package ru.vafeen.reminder.ui.common.screen

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
import ru.vafeen.reminder.noui.viewmodel.RemindersScreenViewModel
import ru.vafeen.reminder.ui.common.components.BottomBar
import ru.vafeen.reminder.ui.common.navigation.ScreenRoute
import ru.vafeen.reminder.ui.theme.ReminderTheme

@Composable
fun RemindersScreen(
    viewModel: RemindersScreenViewModel,
    navController: NavController
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            BottomBar(
                containerColor = ReminderTheme.colors.mainColor,
                selectedRemindersScreen = true,
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
                .background(ReminderTheme.colors.singleTheme)
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "reminders")
        }
    }
}