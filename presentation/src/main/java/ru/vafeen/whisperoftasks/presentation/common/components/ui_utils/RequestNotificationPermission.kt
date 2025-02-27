package ru.vafeen.whisperoftasks.presentation.common.components.ui_utils


import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

/**
 * Компонент для запроса разрешения на отправку уведомлений.
 *
 * Этот компонент запрашивает у пользователя разрешение на отправку уведомлений
 * (POST_NOTIFICATIONS) в случае, если оно еще не было предоставлено.
 * Запрос разрешения осуществляется только на устройствах с Android 13 (API уровень 33) и выше.
 */
@OptIn(ExperimentalPermissionsApi::class)
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
internal fun RequestNotificationPermission() {
    // Создание состояния разрешения для POST_NOTIFICATIONS.
    val notificationPermissionState = rememberPermissionState(
        Manifest.permission.POST_NOTIFICATIONS
    )

    // Если разрешение не предоставлено, запускаем запрос на его получение.
    if (!notificationPermissionState.status.isGranted) {
        LaunchedEffect(key1 = null) {
            notificationPermissionState.launchPermissionRequest() // Запуск запроса разрешения.
        }
    }
}