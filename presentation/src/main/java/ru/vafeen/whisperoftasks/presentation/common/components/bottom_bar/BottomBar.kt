package ru.vafeen.whisperoftasks.presentation.common.components.bottom_bar


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
import androidx.compose.ui.unit.dp
import ru.vafeen.whisperoftasks.presentation.components.navigation.BottomBarNavigator
import ru.vafeen.whisperoftasks.presentation.components.navigation.Screen
import ru.vafeen.whisperoftasks.presentation.utils.suitableColor


/**
 * Компонент нижней панели навигации (Bottom Bar).
 *
 * Этот компонент отображает нижнюю панель навигации с двумя пунктами: "Главная" и "Настройки".
 * Позволяет пользователю переключаться между экранами приложения.
 *
 * @param selectedScreen Текущий выбранный экран [Screen].
 * @param bottomBarNavigator Навигатор для обработки переходов между экранами.
 *                           Может быть null, если навигация не требуется.
 * @param containerColor Цвет фона нижней панели навигации.
 */
@Composable
internal fun BottomBar(
    selectedScreen: Screen,
    bottomBarNavigator: BottomBarNavigator? = null,
    containerColor: Color,
) {
    val countScreens = 3f
    // Настройка цветов для элементов навигации.
    val colors = NavigationBarItemDefaults.colors(
        unselectedIconColor = containerColor.suitableColor().copy(alpha = 0.5f),
        indicatorColor = containerColor,
        disabledIconColor = containerColor.suitableColor(),
    )

    // Создание нижней панели приложения.
    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .height(55.dp),
        containerColor = containerColor
    ) {
        // Элемент навигации для экрана "Главная".

        NavigationBarItem(
            modifier = Modifier.weight(1 / countScreens),
            selected = selectedScreen == Screen.Main,
            onClick = { bottomBarNavigator?.navigateTo(Screen.Main) },
            enabled = selectedScreen != Screen.Main,
            icon = {
                Icon(
                    Icons.Default.Home, contentDescription = "MainScreen"
                )
            },
            colors = colors
        )

        // Элемент навигации для экрана "Напоминания".
        NavigationBarItem(
            modifier = Modifier.weight(1 / countScreens),
            selected = selectedScreen == Screen.Reminders,
            onClick = { bottomBarNavigator?.navigateTo(Screen.Reminders) },
            enabled = selectedScreen != Screen.Reminders,
            icon = {
                Icon(
                    Icons.AutoMirrored.Filled.List, contentDescription = "RemindersScreen"
                )
            },
            colors = colors
        )
        // Элемент навигации для экрана "Настройки".
        NavigationBarItem(
            modifier = Modifier.weight(1 / countScreens),
            selected = selectedScreen == Screen.Settings,
            onClick = { bottomBarNavigator?.navigateTo(Screen.Settings) },
            enabled = selectedScreen != Screen.Settings,

            icon = {
                Icon(
                    Icons.Default.Settings, contentDescription = "SettingsScreen"
                )
            },
            colors = colors
        )
    }
}