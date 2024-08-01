package ru.vafeen.reminder.ui.common.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import ru.vafeen.reminder.noui.viewmodel.MainScreenViewModel

@Composable
fun MainScreen(
    viewModel: MainScreenViewModel,
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "ads")
    }
}