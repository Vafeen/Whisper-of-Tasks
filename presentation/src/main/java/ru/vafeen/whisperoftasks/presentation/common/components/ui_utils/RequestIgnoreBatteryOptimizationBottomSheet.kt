package ru.vafeen.whisperoftasks.presentation.common.components.ui_utils

import android.content.Context
import android.content.Intent
import android.os.PowerManager
import android.provider.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import ru.vafeen.whisperoftasks.resources.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RequestIgnoreBatteryOptimizationBottomSheet() {
    val context = LocalContext.current
    var isBottomSheetShowed by remember { mutableStateOf(false) }
    LaunchedEffect(null) {
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        isBottomSheetShowed = !powerManager.isIgnoringBatteryOptimizations(context.packageName)
    }
    if (isBottomSheetShowed) {
        ModalBottomSheet(onDismissRequest = { isBottomSheetShowed = false }) {
            TextForThisTheme(stringResource(R.string.ignore_battery_optimization_needed), maxLines = 10)
            Button(onClick = { context.startActivity(Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS)) }) {
                TextForThisTheme("switch off")
            }
        }
    }

}