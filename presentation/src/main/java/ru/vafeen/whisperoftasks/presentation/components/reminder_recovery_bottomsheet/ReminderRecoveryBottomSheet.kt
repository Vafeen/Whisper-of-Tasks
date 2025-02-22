package ru.vafeen.whisperoftasks.presentation.components.reminder_recovery_bottomsheet

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import ru.vafeen.whisperoftasks.domain.utils.launchMain
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.TextForThisTheme
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.customMainColorOrDefault
import ru.vafeen.whisperoftasks.presentation.ui.theme.FontSize
import ru.vafeen.whisperoftasks.presentation.ui.theme.Theme
import ru.vafeen.whisperoftasks.resources.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderRecoveryBottomSheet(onDismissRequest: () -> Unit) {
    val cor = rememberCoroutineScope()
    val viewModel: ReminderRecoveryBottomSheetViewModel = koinViewModel()
    val settings by viewModel.settingsFlow.collectAsState()
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        containerColor = Theme.colors.buttonColor
    ) {
        Column(
            modifier = Modifier.padding(30.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextForThisTheme(
                text = stringResource(R.string.recovery_reminders_needed),
                modifier = Modifier.padding(bottom = 50.dp),
                fontSize = FontSize.big22
            )
            Button(shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = settings.customMainColorOrDefault()
                ), onClick = {
                    cor.launchMain {
                        viewModel.recovery()
                        onDismissRequest()
                    }
                }) {
                TextForThisTheme(text = stringResource(R.string.recovery_reminders), maxLines = 5)
            }
        }
    }
}