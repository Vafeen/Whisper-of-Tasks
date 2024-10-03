package ru.vafeen.reminder.noui.permission

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import kotlinx.coroutines.delay
import ru.vafeen.reminder.ui.common.components.ui_utils.DefaultDialog
import ru.vafeen.reminder.ui.common.components.ui_utils.TextForThisTheme
import ru.vafeen.reminder.ui.theme.Theme

@Composable
fun RequestNotificationPermission(context: Context) {
    var ok by remember {
        mutableStateOf(true)
    }
    LaunchedEffect(key1 = null) {
        while (true) {
            ok = ContextCompat.checkSelfPermission(
                context, Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
            if (ok) break
            delay(500)
        }
    }
    if (!ok) {
        DefaultDialog(onDismissRequest = {
            Toast.makeText(context, "ok $ok", Toast.LENGTH_SHORT).show()
            ok = ContextCompat.checkSelfPermission(
                context, Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Theme.colors.singleTheme)
                    .padding(it)
            ) {
                TextForThisTheme(text = "Уведомления выключены, включите!")
                Button(onClick = {
                    if (ContextCompat.checkSelfPermission(
                            context, Manifest.permission.POST_NOTIFICATIONS
                        ) != PackageManager.PERMISSION_GRANTED
                    )
                        context.startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.fromParts("package", context.packageName, null)
                        })
                    else ok = ContextCompat.checkSelfPermission(
                        context, Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_GRANTED
                }) {
                    TextForThisTheme(text = "Ok")
                }
            }
        }
    }
}