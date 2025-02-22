package ru.vafeen.whisperoftasks.presentation.components.reminder_recovery_bottomsheet

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import ru.vafeen.whisperoftasks.domain.utils.launchMain
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.TextForThisTheme
import ru.vafeen.whisperoftasks.presentation.ui.theme.Theme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderRecoveryBottomSheet(onDismissRequest: () -> Unit) {
    val cor = rememberCoroutineScope()
    val viewModel: ReminderRecoveryBottomSheetViewModel = koinViewModel()
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        contentColor = Theme.colors.buttonColor
    ) {
        TextForThisTheme(text = "Устройство было перезагружено, требуется обновление будальников", modifier = Modifier.padding(bottom = 50.dp))
        Button(onClick = {
            cor.launchMain {
                viewModel.recovery()
                onDismissRequest()
            }
        }) {
            TextForThisTheme("recovery")
        }
    }
}