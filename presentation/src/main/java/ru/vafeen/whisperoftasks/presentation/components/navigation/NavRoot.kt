package ru.vafeen.whisperoftasks.presentation.components.navigation

import android.os.Build
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.vafeen.whisperoftasks.domain.domain_models.Release
import ru.vafeen.whisperoftasks.presentation.common.components.bottom_bar.BottomBar
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.RequestIgnoreBatteryOptimizationBottomSheet
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.RequestNotificationPermission
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.UpdateAvailable
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.UpdateProgress
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.customMainColorOrDefault
import ru.vafeen.whisperoftasks.presentation.components.main_screen.MainScreen
import ru.vafeen.whisperoftasks.presentation.components.reminder_recovery_bottomsheet.ReminderRecoveryBottomSheet
import ru.vafeen.whisperoftasks.presentation.components.reminders_screen.RemindersScreen
import ru.vafeen.whisperoftasks.presentation.components.settings_screen.SettingsScreen
import ru.vafeen.whisperoftasks.presentation.components.trash_bin_screen.TrashBinScreen
import ru.vafeen.whisperoftasks.presentation.main.MainActivityViewModel
import ru.vafeen.whisperoftasks.presentation.ui.theme.Theme


@Composable
internal fun NavRoot(viewModel: MainActivityViewModel) {
    viewModel.navController = rememberNavController()

    // Подписка на состояние процесса обновления и процента скачанных данных
    val isUpdateInProcess by viewModel.isUpdateInProcessFlow.collectAsState(false)
    val downloadedPercentage by viewModel.percentageFlow.collectAsState(0f)
    val settings by viewModel.settings.collectAsState()
    var releaseForUpdates: Release? by remember { mutableStateOf(null) }
    var isRecoveryNeededShown by remember { mutableStateOf(false) }

    if (settings.isRecoveryNeeded && !isRecoveryNeededShown) {
        ReminderRecoveryBottomSheet {
            isRecoveryNeededShown = true
        }
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        RequestNotificationPermission()
    }
    RequestIgnoreBatteryOptimizationBottomSheet()
    // Проверка обновлений и отображение нижнего листа с информацией о версии
    LaunchedEffect(null) {
        releaseForUpdates = viewModel.checkUpdates()
    }
    Scaffold(
        containerColor = Theme.colors.background,
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            val selectedScreen by viewModel.currentScreen.collectAsState()
            if (selectedScreen != Screen.TrashBin) {
                BottomBar(
                    selectedScreen = selectedScreen,
                    bottomBarNavigator = viewModel,
                    containerColor = settings.customMainColorOrDefault()
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            NavHost(
                modifier = Modifier.weight(1f),
                navController = viewModel.navController!!, startDestination = Screen.Main,
                enterTransition = { fadeIn(animationSpec = tween()) },
                exitTransition = { fadeOut(animationSpec = tween()) },
                popEnterTransition = { fadeIn(animationSpec = tween()) },
                popExitTransition = { fadeOut(animationSpec = tween()) },
            ) {
                composable<Screen.Main> { MainScreen(viewModel) }
                composable<Screen.Reminders> { RemindersScreen(viewModel) }
                composable<Screen.Settings> { SettingsScreen(viewModel) }
                composable<Screen.TrashBin> { TrashBinScreen(viewModel) }
            }
            releaseForUpdates?.let {
                UpdateAvailable(release = it) {
                    viewModel.update()
                    releaseForUpdates = null
                }
            }
            // Показывать индикатор загрузки, если обновление в процессе
            if (isUpdateInProcess) UpdateProgress(percentage = downloadedPercentage)
        }
    }
}