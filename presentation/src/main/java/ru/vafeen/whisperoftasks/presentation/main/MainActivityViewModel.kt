package ru.vafeen.whisperoftasks.presentation.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.toRoute
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.vafeen.whisperoftasks.presentation.components.navigation.BottomBarNavigator
import ru.vafeen.whisperoftasks.presentation.components.navigation.Screen

internal class MainActivityViewModel() : ViewModel(), BottomBarNavigator {
    /**
     * Экран, отображаемый при запуске приложения.
     */
    val startScreen = Screen.Main

    /**
     * Максимальное количество экранов, которые могут быть в стеке навигации (2 пользовательских + 1 базовый).
     */
    val maxCountOfScreens = 3

    /**
     * Контроллер навигации для управления переходами между экранами.
     */
    override var navController: NavHostController? = null

    /**
     * Поток состояния, отслеживающий текущий активный экран.
     */
    private val _currentScreen: MutableStateFlow<Screen> = MutableStateFlow(startScreen)
    override val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    /**
     * Обновляет состояние текущего экрана, отслеживая изменения в backStack навигации.
     */
    private fun emitCurrentScreen() {
        viewModelScope.launch(Dispatchers.Main) {
            navController?.currentBackStackEntryFlow?.collect { backStackEntry ->
                val destination = backStackEntry.destination
                when {
                    destination.hasRoute(Screen.Main::class) -> _currentScreen.emit(Screen.Main)
                    destination.hasRoute(Screen.Reminders::class) -> _currentScreen.emit(Screen.Reminders)
                    destination.hasRoute(Screen.Settings::class) -> _currentScreen.emit(Screen.Settings)
                }
            }
        }
    }

    /**
     * Обрабатывает действие "Назад", переходя на предыдущий экран.
     */
    override fun back() {
        navController?.popBackStack()
        emitCurrentScreen()
    }

    /**
     * Выполняет переход на указанный экран.
     *
     * @param screen Целевой экран для перехода.
     */
    @SuppressLint("RestrictedApi")
    override fun navigateTo(screen: Screen) {
        if (screen != Screen.Main) {
            navController?.currentBackStack?.value?.size?.let { currentStackSize ->
                if (currentStackSize == maxCountOfScreens) {
                    navController?.popBackStack()
                }
            }
            navController?.navigate(screen)
        } else {
            navController?.popBackStack()
        }
        emitCurrentScreen()
    }
}
