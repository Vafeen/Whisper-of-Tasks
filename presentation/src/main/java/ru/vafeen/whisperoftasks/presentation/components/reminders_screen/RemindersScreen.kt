package ru.vafeen.whisperoftasks.presentation.components.reminders_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import org.koin.androidx.compose.koinViewModel
import ru.vafeen.whisperoftasks.domain.domain_models.Reminder
import ru.vafeen.whisperoftasks.domain.duration.RepeatDuration
import ru.vafeen.whisperoftasks.domain.utils.nullTime
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.ReminderCard
import ru.vafeen.whisperoftasks.presentation.components.reminder_dialog.ReminderDialog
import ru.vafeen.whisperoftasks.presentation.ui.theme.Theme
import java.time.LocalDate
import java.time.LocalDateTime


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RemindersScreen() {
    val viewModel: RemindersScreenViewModel = koinViewModel()
    val reminders by viewModel.remindersFlow.collectAsState()
    var isAddingReminder by remember {
        mutableStateOf(false)
    }
    val lastReminder: MutableState<Reminder?> = remember {
        mutableStateOf(null)
    }
    if (isAddingReminder) {
        if (lastReminder.value == null) {
            lastReminder.value = Reminder(
                title = "",
                text = "",
                dt = LocalDateTime.of(LocalDate.now(), nullTime),
                idOfReminder = 0,
                repeatDuration = RepeatDuration.NoRepeat
            )
        }
        ReminderDialog(
            newReminder = lastReminder as MutableState<Reminder>, // safety is above
            onDismissRequest = {
                isAddingReminder = false
            }
        )

    }
    Scaffold(
        containerColor = Theme.colors.background, modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { isAddingReminder = true },
                containerColor = Theme.colors.mainColor
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(ru.vafeen.whisperoftasks.resources.R.string.add_reminder)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(items = reminders) {
                it.ReminderCard(modifier = Modifier.clickable {
                    lastReminder.value = it
                    isAddingReminder = true
                })
            }
        }
    }
}