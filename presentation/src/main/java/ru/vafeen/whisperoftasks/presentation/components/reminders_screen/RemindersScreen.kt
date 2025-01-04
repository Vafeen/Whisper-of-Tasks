package ru.vafeen.whisperoftasks.presentation.components.reminders_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel
import ru.vafeen.whisperoftasks.presentation.components.ReminderCard
import ru.vafeen.whisperoftasks.presentation.ui.theme.Theme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RemindersScreen() {
    val viewModel: RemindersScreenViewModel = koinViewModel()
    val reminders by viewModel.remindersFlow.collectAsState()
    Scaffold(
        containerColor = Theme.colors.background, modifier = Modifier.fillMaxSize()
    ) {
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(items = reminders) {
                it.ReminderCard()
            }
        }

    }
}