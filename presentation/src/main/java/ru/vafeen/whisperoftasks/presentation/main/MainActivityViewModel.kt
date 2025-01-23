package ru.vafeen.whisperoftasks.presentation.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import ru.vafeen.whisperoftasks.domain.domain_models.Release
import ru.vafeen.whisperoftasks.domain.domain_models.Settings
import ru.vafeen.whisperoftasks.domain.network.result.DownloadStatus
import ru.vafeen.whisperoftasks.domain.network.service.Refresher
import ru.vafeen.whisperoftasks.domain.network.usecase.GetLatestReleaseUseCase
import ru.vafeen.whisperoftasks.domain.shared_preferences.SettingsManager
import ru.vafeen.whisperoftasks.domain.utils.Link
import ru.vafeen.whisperoftasks.domain.utils.RefresherInfo
import ru.vafeen.whisperoftasks.domain.utils.copyTextToClipBoard
import ru.vafeen.whisperoftasks.domain.utils.getVersionName
import ru.vafeen.whisperoftasks.presentation.components.navigation.BottomBarNavigator
import ru.vafeen.whisperoftasks.presentation.components.navigation.Screen
import kotlin.system.exitProcess

/**
 * ViewModel для управления состоянием главной активности приложения.
 * Обрабатывает обновления, миграцию данных, навигацию и общие ошибки.
 *
 * @property getLatestReleaseUseCase Юзкейс для получения последней версии приложения.
 * @property context Контекст приложения для работы с ресурсами и управления ошибками.
 * @property settingsManager Менеджер для работы с настройками приложения.
 * @property refresher Сервис для скачивания и обновлений.
 * @property release Последняя доступная версия приложения.
 * @property versionCode Код версии приложения.
 * @property versionName Имя версии приложения.
 * @property isUpdateInProcessFlow Поток состояния, отслеживающий процесс обновления.
 * @property percentageFlow Поток состояния, отслеживающий процент выполнения обновления.
 * @property settings Поток состояния, содержащий текущие настройки приложения.
 * @property startScreen Экран, который должен отображаться при запуске приложения.
 * @property navController Контроллер навигации, управляющий переходами между экранами.
 * @property currentScreen Поток состояния, отслеживающий текущий экран приложения.
 */
internal class MainActivityViewModel(
    private val getLatestReleaseUseCase: GetLatestReleaseUseCase,
    context: Context,
    private val settingsManager: SettingsManager,
    private val refresher: Refresher,
) : ViewModel(), BottomBarNavigator {

    private var release: Release? = null

    private val versionName = context.getVersionName()

    /**
     * Проверяет наличие обновлений приложения.
     *
     * @return Последняя версия приложения, если она отличается от текущей, или null в противном случае.
     */
    suspend fun checkUpdates(): Release? {
        val localRelease = getLatestReleaseUseCase.invoke()
        return if (localRelease != null && versionName != null &&
            localRelease.tagName.substringAfter("v") != versionName
        ) {
            Log.d("release", "${localRelease.tagName.substringAfter("v")} != ${versionName}")
            saveSettingsToSharedPreferences {
                it.copy(releaseBody = localRelease.body)
            }
            release = localRelease
            release
        } else null
    }

    /**
     * Запускает процесс обновления приложения.
     */
    fun update() {
        release?.let {
            viewModelScope.launch(Dispatchers.IO) {
                refresher.refresh(viewModelScope, it.apkUrl, RefresherInfo.APK_FILE_NAME)
            }
        }
    }

    /**
     * Поток состояния, отслеживающий процесс обновления.
     * Возвращает true, если обновление находится в процессе, и false в противном случае.
     */
    val isUpdateInProcessFlow: SharedFlow<Boolean> =
        refresher.progressFlow.map {
            it !is DownloadStatus.Error && it !is DownloadStatus.Success
        }.shareIn(viewModelScope, SharingStarted.Lazily)

    /**
     * Поток состояния, отслеживающий процент выполнения обновления.
     * Возвращает процент выполнения или 0, если обновление не начато или завершено.
     */
    val percentageFlow: SharedFlow<Float> =
        refresher.progressFlow.map {
            when (it) {
                is DownloadStatus.InProgress -> it.percentage
                DownloadStatus.Success -> 100f
                else -> 0f
            }
        }.shareIn(viewModelScope, SharingStarted.Lazily)

    /**
     * Поток состояния, содержащий текущие настройки приложения.
     */
    val settings = settingsManager.settingsFlow

    /**
     * Функция для сохранения настроек в SharedPreferences.
     * Принимает функцию, изменяющую текущие настройки.
     *
     * @param saving Функция, изменяющая настройки.
     */
    private fun saveSettingsToSharedPreferences(saving: (Settings) -> Settings) {
        settingsManager.save(saving)
    }

    /**
     * Регистрирует обработчик необработанных исключений для приложения.
     * В случае ошибки копирует информацию об ошибке в буфер обмена и завершает процесс.
     *
     * @param context Контекст приложения, используемый для работы с буфером обмена.
     */
    private fun registerGeneralExceptionCallback(context: Context) {
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            context.copyTextToClipBoard(
                label = "Error",
                text = "Contact us about this problem: ${Link.MAIL}\n\n Exception in ${thread.name} thread\n${throwable.stackTraceToString()}"
            )
            Log.e("GeneralException", "Exception in thread ${thread.name}", throwable)
            exitProcess(0)
        }
    }

    init {
        // Регистрируем обработчик исключений при инициализации ViewModel.
        registerGeneralExceptionCallback(context = context)
    }

    /**
     * Экран, который должен отображаться при запуске приложения.
     */
    private val startScreen = Screen.Main

    /**
     * Контроллер навигации, управляющий переходами между экранами.
     */
    override var navController: NavHostController? = null

    /**
     * Поток состояния, отслеживающий текущий экран приложения.
     */
    private val _currentScreen: MutableStateFlow<Screen> = MutableStateFlow(startScreen)
    override val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    /**
     * Эмитирует текущий экран на основе стека навигации.
     * Отслеживает изменения в backStack навигации и обновляет состояние текущего экрана.
     */
    private fun emitCurrentScreen() {
        viewModelScope.launch(Dispatchers.Main) {
            navController?.currentBackStackEntryFlow?.collect { backStackEntry ->
                val destination = backStackEntry.destination
                when {
                    destination.hasRoute(Screen.Main::class) -> _currentScreen.emit(Screen.Main)
                    destination.hasRoute(Screen.Settings::class) -> _currentScreen.emit(Screen.Settings)
                    destination.hasRoute(Screen.Reminders::class) -> _currentScreen.emit(Screen.Reminders)
                }
            }
        }
    }

    /**
     * Обрабатывает действие "Назад". Переходит на предыдущий экран в навигации.
     */
    override fun back() {
        navController?.popBackStack()
        emitCurrentScreen()
    }

    /**
     * Переходит на указанный экран.
     *
     * @param screen Целевой экран для навигации.
     */
    override fun navigateTo(screen: Screen) {
        val currentDestination = navController?.currentBackStackEntry?.destination
        if (currentDestination?.hasRoute(Screen.Main::class) == false) navController?.popBackStack()
        if (currentDestination?.hasRoute(Screen.Main::class) == true || screen != Screen.Main)
            navController?.navigate(screen)
        emitCurrentScreen()
    }
}