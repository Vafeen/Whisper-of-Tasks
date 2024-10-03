package ru.vafeen.reminder.ui.common.screen

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.vafeen.reminder.network.downloader.Downloader
import ru.vafeen.reminder.network.downloader.Progress
import ru.vafeen.reminder.noui.duration.RepeatDuration
import ru.vafeen.reminder.noui.local_database.entity.Reminder
import ru.vafeen.reminder.ui.common.components.bottom_bar.BottomBar
import ru.vafeen.reminder.ui.common.components.ui_utils.ReminderDataString
import ru.vafeen.reminder.ui.common.components.ui_utils.TextForThisTheme
import ru.vafeen.reminder.ui.common.components.ui_utils.UpdateProgress
import ru.vafeen.reminder.ui.common.navigation.ScreenRoute
import ru.vafeen.reminder.ui.common.viewmodel.MainScreenViewModel
import ru.vafeen.reminder.ui.theme.FontSize
import ru.vafeen.reminder.ui.theme.Theme
import ru.vafeen.reminder.utils.Path
import ru.vafeen.reminder.utils.getDateStringWithDayOfWeek
import ru.vafeen.reminder.utils.getMainColorForThisTheme
import ru.vafeen.reminder.utils.suitableColor
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun MainScreen(
    navController: NavController, viewModel: MainScreenViewModel
) {
    val context = LocalContext.current
    val defaultColor = Theme.colors.mainColor
    val progress = remember {
        mutableStateOf(Progress(totalBytesRead = 0L, contentLength = 0L, done = false))
    }
    val dark = isSystemInDarkTheme()
    val mainColor by remember {
        mutableStateOf(
            viewModel.settings.getMainColorForThisTheme(isDark = dark) ?: defaultColor
        )
    }
    var isUpdateInProcess by remember {
        mutableStateOf(false)
    }
    val cor = rememberCoroutineScope()
    var localTime by remember {
        mutableStateOf(LocalTime.now())
    }
    var localDate by remember {
        mutableStateOf(LocalDate.now())
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
                        context = context, apkFilePath = Path.path(context)
                    )
                }
            } else isUpdateInProcess = false
        }
    }

    var reminders by remember {
        mutableStateOf(listOf<Reminder>())
    }
    val cardsWithDateState = rememberLazyListState()

    LaunchedEffect(key1 = null) {
        viewModel.databaseRepository.getAllRemindersAsFlow().collect {
            reminders = it
        }
    }

    val pagerState = rememberPagerState(
        pageCount = {
            viewModel.pageNumber
        }, initialPage = 0
    )
    BackHandler {
        when {
            pagerState.currentPage == 0 -> {
                navController.popBackStack()
                (context as Activity).finish()
            }

            else -> {
                cor.launch(Dispatchers.Main) {
                    pagerState.animateScrollToPage(0)
                }
            }
        }
    }

    LaunchedEffect(key1 = null) {
        withContext(Dispatchers.Main) {
            while (true) {
                localTime = LocalTime.now()
                delay(timeMillis = 1000L)
            }
        }
    }

    Scaffold(containerColor = Theme.colors.singleTheme,
        bottomBar = {
            BottomBar(
                containerColor = Theme.colors.mainColor,
                selectedMainScreen = true,
                navigateToRemindersScreen = { navController.navigate(ScreenRoute.Reminders.route) },
                navigateToSettingsScreen = { navController.navigate(ScreenRoute.Settings.route) })
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyRow(
                state = cardsWithDateState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                items(count = viewModel.pageNumber) { index ->
                    val day = viewModel.todayDate.plusDays(index.toLong())
                    Card(modifier = Modifier
                        .padding(horizontal = 3.dp)
                        .clickable {
                            cor.launch(Dispatchers.Main) {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                        .alpha(
                            if (day != localDate && day != viewModel.todayDate) 0.5f else 1f
                        ), colors = CardDefaults.cardColors(
                        containerColor = if (day == viewModel.todayDate) mainColor
                        else Theme.colors.buttonColor,
                        contentColor = (if (day == viewModel.todayDate) mainColor
                        else Theme.colors.buttonColor).suitableColor()
                    )) {
                        Text(
                            text = day.getDateStringWithDayOfWeek(ruDaysOfWeek = viewModel.ruDaysOfWeek),
                            fontSize = FontSize.small17,
                            modifier = Modifier.padding(
                                vertical = 5.dp, horizontal = 10.dp
                            )
                        )
                    }
                }
            }
            HorizontalPager(
                state = pagerState, modifier = Modifier.weight(10f)
            ) { page ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    val thisDate = viewModel.todayDate.plusDays(page.toLong())
                    localDate = viewModel.todayDate.plusDays(pagerState.currentPage.toLong())
                    if (!pagerState.isScrollInProgress) LaunchedEffect(key1 = null) {
                        cardsWithDateState.animateScrollToItem(pagerState.currentPage)
                    }
                    val remindersForThisDay = reminders.filter {
                        it.dt.toLocalDate() == thisDate ||
                                it.repeatDuration == RepeatDuration.EveryDay ||
                                it.repeatDuration == RepeatDuration.EveryWeek && it.dt.dayOfWeek == thisDate.dayOfWeek
                    }
                    val lostReminders = reminders.filter {
                        it.dt.toLocalDate() < thisDate
                    }
                    TextForThisTheme(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = "События в этот день\\"
                    )
                    for (reminder in remindersForThisDay) {
                        reminder.ReminderDataString(viewModel = viewModel)
                    }
                    TextForThisTheme(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = "Прошедшие события\\"
                    )
                    if (lostReminders.isNotEmpty()) {

                        for (reminder in lostReminders) {
                            reminder.ReminderDataString(viewModel = viewModel)
                        }
                    }
                }
            }
            if (isUpdateInProcess) UpdateProgress(percentage = progress)
        }
    }
}
