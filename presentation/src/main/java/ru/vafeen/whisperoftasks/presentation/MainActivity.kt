package ru.vafeen.whisperoftasks.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ru.vafeen.whisperoftasks.presentation.common.navigation.NavRoot
import ru.vafeen.whisperoftasks.presentation.ui.theme.ReminderTheme

internal class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReminderTheme {
                NavRoot()
            }
        }
    }
}
