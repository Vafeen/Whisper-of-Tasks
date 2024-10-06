package ru.vafeen.whisperoftasks.ui.common.components.bottom_bar

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import ru.vafeen.whisperoftasks.utils.suitableColor


@Composable
fun BottomBar(
    containerColor: Color,
    navigateToMainScreen: () -> Unit = {},
    navigateToRemindersScreen: () -> Unit = {},
    navigateToSettingsScreen: () -> Unit = {},
    selectedMainScreen: Boolean = false,
    selectedRemindersScreen: Boolean = false,
    selectedSettingsScreen: Boolean = false,
) {
    val context = LocalContext.current
    val colors = NavigationBarItemDefaults.colors(
        unselectedIconColor = containerColor.suitableColor().copy(alpha = 0.5f),
        indicatorColor = containerColor,
        disabledIconColor = containerColor.suitableColor(),
    )
    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .height(55.dp),
        containerColor = containerColor
    ) {

        NavigationBarItem(
            selected = selectedMainScreen,
            enabled = !selectedMainScreen,
            modifier = Modifier.weight(1 / 3f),
            onClick = navigateToMainScreen,
            icon = {
                Icon(
                    Icons.Default.Home,
                    contentDescription = "MainScreen"
                )
            },
            colors = colors
        )

        NavigationBarItem(
            selected = selectedRemindersScreen,
            enabled = !selectedRemindersScreen,
            modifier = Modifier.weight(1 / 3f),
            onClick = navigateToRemindersScreen,
            icon = {
                Icon(
                    Icons.AutoMirrored.Filled.List,
                    contentDescription = "RemindersScreen"
                )
            },
            colors = colors
        )
        NavigationBarItem(
            selected = selectedSettingsScreen,
            enabled = !selectedSettingsScreen,
            modifier = Modifier.weight(1 / 3f),
            onClick = {
                Toast.makeText(context, "There is no screen here.", Toast.LENGTH_SHORT).show()
            }//navigateToSettingsScreen
            ,
            icon = {
                Icon(
                    Icons.Default.Settings,
                    contentDescription = "SettingsScreen"
                )
            },
            colors = colors
        )
    }
}
