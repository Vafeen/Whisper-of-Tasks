package ru.vafeen.whisperoftasks.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import org.koin.androidx.compose.koinViewModel
import ru.vafeen.whisperoftasks.presentation.components.navigation.NavRoot
import ru.vafeen.whisperoftasks.presentation.ui.theme.MainTheme

internal class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel = koinViewModel<MainActivityViewModel>()
            MainTheme {
                NavRoot(viewModel)
            }
        }
    }
}
