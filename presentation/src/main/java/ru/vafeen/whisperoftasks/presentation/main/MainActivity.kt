package ru.vafeen.whisperoftasks.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import ru.vafeen.whisperoftasks.presentation.components.navigation.NavRoot
import ru.vafeen.whisperoftasks.presentation.ui.theme.ReminderTheme

internal class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainActivityViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReminderTheme {
                NavRoot(viewModel)
            }
        }
    }
}
