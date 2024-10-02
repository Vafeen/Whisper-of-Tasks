package ru.vafeen.reminder.ui.common.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import ru.vafeen.reminder.network.downloader.Downloader
import ru.vafeen.reminder.network.downloader.Progress
import ru.vafeen.reminder.ui.common.components.BottomBar
import ru.vafeen.reminder.ui.common.components.ui_utils.UpdateProgress
import ru.vafeen.reminder.ui.common.navigation.ScreenRoute
import ru.vafeen.reminder.ui.common.viewmodel.MainScreenViewModel
import ru.vafeen.reminder.ui.theme.Theme
import ru.vafeen.reminder.utils.Path

@Composable
fun MainScreen(
    viewModel: MainScreenViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    var isUpdateInProcess by remember {
        mutableStateOf(false)
    }
    val progress = remember {
        mutableStateOf(Progress(totalBytesRead = 0L, contentLength = 0L, done = false))
    }
    LaunchedEffect(key1 = null) {
        Downloader.isUpdateInProcessFlow.collect {
            isUpdateInProcess = it
        }
    }
    LaunchedEffect(key1 = null) {
        Downloader.sizeFlow.collect {
            if (!it.failed) {
                progress.value = it
                if (it.contentLength == it.totalBytesRead) {
                    isUpdateInProcess = false
                    Downloader.installApk(
                        context = context, apkFilePath = Path.path(context).toString()
                    )
                }
            } else isUpdateInProcess = false
        }
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            BottomBar(
                containerColor = Theme.colors.mainColor,
                selectedMainScreen = true,
                navigateToRemindersScreen = { navController.navigate(ScreenRoute.Reminders.route) },
                navigateToSettingsScreen = { navController.navigate(ScreenRoute.Settings.route) })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.colors.singleTheme)
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "main",
                color = Theme.colors.oppositeTheme
            )
            if (isUpdateInProcess) UpdateProgress(percentage = progress)
        }
    }

}