package ru.vafeen.whisperoftasks.presentation.components.main_screen

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.koinViewModel
import ru.vafeen.whisperoftasks.domain.domain_models.Reminder
import ru.vafeen.whisperoftasks.domain.duration.RepeatDuration
import ru.vafeen.whisperoftasks.domain.utils.getDateStringWithWeekOfDay
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.DeleteReminders
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.ReminderDataString
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.TextForThisTheme
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.customMainColorOrDefault
import ru.vafeen.whisperoftasks.presentation.components.navigation.BottomBarNavigator
import ru.vafeen.whisperoftasks.presentation.components.reminder_dialog.ReminderDialog
import ru.vafeen.whisperoftasks.presentation.ui.theme.FontSize
import ru.vafeen.whisperoftasks.presentation.ui.theme.Theme
import ru.vafeen.whisperoftasks.presentation.utils.DatePickerInfo
import ru.vafeen.whisperoftasks.presentation.utils.suitableColor
import ru.vafeen.whisperoftasks.resources.R
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
internal fun MainScreen(bottomBarNavigator: BottomBarNavigator) {
    val viewModel: MainScreenViewModel = koinViewModel()
    val context = LocalContext.current
    val dark = isSystemInDarkTheme()
    val settings by viewModel.settings.collectAsState()
    val mainColor = settings.customMainColorOrDefault(dark)

    val cor = rememberCoroutineScope()
    var localTime by remember {
        mutableStateOf(LocalTime.now())
    }
    var localDate by remember {
        mutableStateOf(LocalDate.now())
    }
    var isEditingReminder by remember {
        mutableStateOf(false)
    }
    val lastReminder: MutableState<Reminder?> =
        remember {
            mutableStateOf(null)
        }
    val isDeletingInProcess by remember {
        derivedStateOf {
            viewModel.remindersForDeleting.isNotEmpty()
        }
    }

    fun Modifier.combinedClickableForRemovingReminder(reminder: Reminder): Modifier =
        this.combinedClickable(
            onClick = {
                if (!isDeletingInProcess) {
                    lastReminder.value = reminder
                    isEditingReminder = true
                } else {
                    viewModel.changeStatusForDeleting(reminder)
                }
            },
            onLongClick = {
                if (!isDeletingInProcess) {
                    viewModel.setReminderAsCandidateForDeleting(reminder)
                } else {
                    viewModel.clearRemindersForDeleting()
                }
            }
        )


    val reminders by viewModel.remindersFlow.collectAsState(listOf())
    val cardsWithDateState =
        rememberLazyListState(initialFirstVisibleItemIndex = DatePickerInfo.countOfDaysInPast - 1)

    val pagerState = rememberPagerState(
        pageCount = {
            DatePickerInfo.pageNumber
        }, initialPage = DatePickerInfo.countOfDaysInPast
    )
    BackHandler {
        when {
            isDeletingInProcess -> {
                viewModel.clearRemindersForDeleting()
            }

            !isDeletingInProcess && pagerState.currentPage == DatePickerInfo.countOfDaysInPast -> {
                bottomBarNavigator.back()
                (context as Activity).finish()
            }

            else -> {
                cor.launch(Dispatchers.Main) {
                    pagerState.animateScrollToPage(DatePickerInfo.countOfDaysInPast)
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
    LaunchedEffect(key1 = pagerState.currentPage) {
        cardsWithDateState.animateScrollToItem(
            if (pagerState.currentPage > 0) pagerState.currentPage - 1
            else pagerState.currentPage
        )
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Theme.colors.background,
        topBar = {
            TopAppBar(colors = TopAppBarColors(
                containerColor = Theme.colors.singleTheme,
                scrolledContainerColor = Theme.colors.singleTheme,
                navigationIconContentColor = Theme.colors.oppositeTheme,
                titleContentColor = Theme.colors.oppositeTheme,
                actionIconContentColor = Theme.colors.singleTheme
            ),
                modifier = Modifier.fillMaxWidth(),
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextForThisTheme(
                            text = stringResource(id = R.string.tasks_by_day),
                            fontSize = FontSize.huge27
                        )
                    }

                })

        }
    ) { innerPadding ->
        val x = innerPadding
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
        ) {
            if (isEditingReminder) {
                ReminderDialog(
                    newReminder = lastReminder as MutableState<Reminder>, // safety is above
                    onDismissRequest = { isEditingReminder = false }
                )
            }
            LazyRow(
                state = cardsWithDateState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                itemsIndexed(DatePickerInfo.dateList()) { index, day ->
                    Column {
                        Card(
                            modifier = Modifier
                                .fillParentMaxWidth(1 / 3f)
                                .padding(horizontal = 5.dp)
                                .clickable {
                                    cor.launch(Dispatchers.Main) {
                                        pagerState.animateScrollToPage(index)
                                    }
                                },
                            colors = CardDefaults.cardColors(
                                containerColor = if (day == viewModel.todayDate) mainColor
                                else Theme.colors.buttonColor,
                                contentColor = (if (day == viewModel.todayDate) mainColor
                                else Theme.colors.buttonColor).suitableColor()
                            ), elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
                        ) {
                            Text(
                                text = day.getDateStringWithWeekOfDay(context = context),
                                fontSize = FontSize.small17,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        vertical = 5.dp, horizontal = 10.dp
                                    ),
                                textAlign = TextAlign.Center
                            )
                        }
                        if (day == localDate)
                            Card(
                                modifier = Modifier
                                    .fillParentMaxWidth(1 / 3f)
                                    .padding(vertical = 2.dp)
                                    .padding(horizontal = 18.dp)
                                    .height(2.dp),
                                colors = CardDefaults.cardColors(containerColor = Theme.colors.oppositeTheme)
                            ) {}
                    }
                }
            }
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .weight(10f)
            ) { page ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    val startDateInPast = DatePickerInfo.startDateInPast()
                    val dateOfThisPage = startDateInPast.plusDays(page.toLong())
                    localDate =
                        startDateInPast.plusDays(pagerState.currentPage.toLong())
                    val remindersForThisDay = reminders.filter {
                        val d = it.dt.toLocalDate()
                        d == dateOfThisPage ||
                                it.repeatDuration == RepeatDuration.EveryDay && d <= dateOfThisPage ||
                                it.repeatDuration == RepeatDuration.EveryWeek && it.dt.dayOfWeek == dateOfThisPage.dayOfWeek
                    }
                    val lostReminders = reminders.filter {
                        it.repeatDuration == RepeatDuration.NoRepeat &&
                                dateOfThisPage > it.dt.toLocalDate()
                    }
                    if (remindersForThisDay.isEmpty() && lostReminders.isEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            TextForThisTheme(
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                text = stringResource(id = R.string.no_events_today),
                                fontSize = FontSize.big22
                            )
                        }
                    }

                    if (remindersForThisDay.isNotEmpty()) {
                        remindersForThisDay.forEach {
                            it.ReminderDataString(
                                mainColor = mainColor,
                                modifier = Modifier.combinedClickableForRemovingReminder(reminder = it),
                                viewModel = viewModel,
                                dateOfThisPage = dateOfThisPage,
                                isItCandidateForDelete = viewModel.remindersForDeleting.contains(it.idOfReminder),
                                changeStatusOfDeleting = if (isDeletingInProcess) {
                                    { viewModel.changeStatusForDeleting(it) }
                                } else null,
                            )
                        }
                    }

                    if (lostReminders.isNotEmpty()) {
                        TextForThisTheme(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            text = stringResource(id = R.string.past_events),
                            fontSize = FontSize.medium19
                        )
                        lostReminders.forEach {
                            it.ReminderDataString(
                                mainColor = mainColor,
                                modifier = Modifier.combinedClickableForRemovingReminder(reminder = it),
                                viewModel = viewModel,
                                dateOfThisPage = dateOfThisPage,
                                isItCandidateForDelete = viewModel.remindersForDeleting.contains(it.idOfReminder),
                                changeStatusOfDeleting = if (isDeletingInProcess) {
                                    { viewModel.changeStatusForDeleting(it) }
                                } else null,
                            )
                        }
                    }
                }
            }
            if (isDeletingInProcess) DeleteReminders {
                cor.launch {
                    viewModel.unsetEventsAndRemoveRemindersForRemoving()
                }
            }
        }
    }
}