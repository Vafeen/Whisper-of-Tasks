package ru.vafeen.reminder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.vafeen.reminder.ui.navigation.Screen
import ru.vafeen.reminder.ui.screen.MainScreen
import ru.vafeen.reminder.ui.theme.ReminderTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReminderTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Screen.Main.route) {
                    composable(route = Screen.Main.route) {
                        MainScreen()
                    }
                }
            }
        }
    }
}

