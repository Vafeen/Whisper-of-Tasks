package ru.vafeen.whisperoftasks.presentation.common.screen

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel
import ru.vafeen.whisperoftasks.domain.utils.Link
import ru.vafeen.whisperoftasks.resources.R
import ru.vafeen.whisperoftasks.domain.utils.getMainColorForThisTheme
import ru.vafeen.whisperoftasks.domain.utils.getVersionName
import ru.vafeen.whisperoftasks.presentation.common.components.bottom_bar.BottomBar
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.CardOfSettings
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.ColorPickerDialog
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.TextForThisTheme
import ru.vafeen.whisperoftasks.presentation.common.components.video.AssetsInfo
import ru.vafeen.whisperoftasks.presentation.common.components.video.GifPlayer
import ru.vafeen.whisperoftasks.presentation.common.navigation.ScreenRoute
import ru.vafeen.whisperoftasks.presentation.common.viewmodel.SettingsScreenViewModel
import ru.vafeen.whisperoftasks.presentation.ui.theme.FontSize
import ru.vafeen.whisperoftasks.presentation.ui.theme.Theme
import ru.vafeen.whisperoftasks.presentation.utils.openLink
import ru.vafeen.whisperoftasks.presentation.utils.sendEmail
import ru.vafeen.whisperoftasks.presentation.utils.suitableColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
) {
    val viewModel: SettingsScreenViewModel = koinViewModel()
    val context = LocalContext.current
    val dark = isSystemInDarkTheme()
    val settings by viewModel.settings.collectAsState()
    val mainColor = settings.getMainColorForThisTheme(isDark = dark) ?: Theme.colors.mainColor
    var colorIsEditable by remember {
        mutableStateOf(false)
    }
    val gotoMainScreenCallBack = {
        navController.popBackStack()
        navController.popBackStack()
        navController.navigate(ScreenRoute.Main.route)
    }
    val checkBoxColors = CheckboxDefaults.colors(
        checkedColor = Theme.colors.oppositeTheme,
        checkmarkColor = Theme.colors.singleTheme,
        uncheckedColor = Theme.colors.oppositeTheme
    )
    var catsOnUIIsChanging by remember {
        mutableStateOf(false)
    }


    BackHandler(onBack = gotoMainScreenCallBack)

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(colors = TopAppBarColors(
                containerColor = Theme.colors.singleTheme,
                scrolledContainerColor = Theme.colors.singleTheme,
                navigationIconContentColor = Theme.colors.oppositeTheme,
                titleContentColor = Theme.colors.oppositeTheme,
                actionIconContentColor = Theme.colors.singleTheme
            ), modifier = Modifier.fillMaxWidth(), title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextForThisTheme(
                        text = stringResource(R.string.settings), fontSize = FontSize.big22
                    )
                }
            })
        },
        bottomBar = {
            BottomBar(
                containerColor = mainColor,
                selectedSettingsScreen = true,
                navigateToRemindersScreen = {
                    navController.popBackStack()
                    navController.navigate(ScreenRoute.Reminders.route)
                },
                navigateToMainScreen = {
                    navController.popBackStack()
                    navController.popBackStack()
                    navController.navigate(ScreenRoute.Main.route)
                })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Theme.colors.singleTheme)
        ) {

            if (colorIsEditable) ColorPickerDialog(context = context,
                firstColor = mainColor,
                onDismissRequest = { colorIsEditable = false }) {
                viewModel.saveSettingsToSharedPreferences(
                    if (dark) settings.copy(
                        darkThemeColor = it
                    ) else settings.copy(lightThemeColor = it)
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 20.dp)
                    .verticalScroll(rememberScrollState()),
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    // name of section
                    TextForThisTheme(
                        modifier = Modifier
                            .padding(10.dp)
                            .align(Alignment.Center),
                        fontSize = FontSize.big22,
                        text = stringResource(R.string.interface_str)
                    )
                    if (settings.catInSettings)
                        GifPlayer(
                            size = 80.dp,
                            modifier = Modifier.align(Alignment.CenterEnd),
                            imageUri = Uri.parse(AssetsInfo.FUNNY_SETTINGS_CAT)
                        )
                }


                // Color
                CardOfSettings(
                    text = stringResource(R.string.interface_color),
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.palette),
                            contentDescription = "change color of interface",
                            tint = it.suitableColor()
                        )
                    }, onClick = { colorIsEditable = true }
                )
                // cats in interface
                CardOfSettings(
                    text = stringResource(R.string.cats_on_ui),
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.cat),
                            contentDescription = "cats in interface",
                            tint = it.suitableColor()
                        )
                    }, onClick = { catsOnUIIsChanging = !catsOnUIIsChanging },
                    additionalContentIsVisible = catsOnUIIsChanging,
                    additionalContent = {
                        Column {
                            val onCheckedChangeCatInSettings = {
                                viewModel.saveSettingsToSharedPreferences(
                                    settings = settings.copy(
                                        catInSettings = !settings.catInSettings
                                    )
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onCheckedChangeCatInSettings() }
                                    .padding(horizontal = it),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                TextForThisTheme(
                                    text = stringResource(R.string.cat_in_settings),
                                    fontSize = FontSize.medium19
                                )
                                Checkbox(
                                    checked = settings.catInSettings,
                                    onCheckedChange = {
                                        onCheckedChangeCatInSettings()
                                    }, colors = checkBoxColors
                                )
                            }

                        }
                    }
                )


                // name of section
                TextForThisTheme(
                    modifier = Modifier
                        .padding(10.dp)
                        .align(Alignment.CenterHorizontally),
                    fontSize = FontSize.big22,
                    text = stringResource(R.string.contacts)
                )

                // CODE
                CardOfSettings(
                    text = stringResource(R.string.code),
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.terminal),
                            contentDescription = "view code",
                            tint = it.suitableColor()
                        )
                    }, onClick = {
                        context.openLink(link = Link.CODE)
                    }
                )

                CardOfSettings(
                    text = stringResource(R.string.report_a_bug),
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.bug_report),
                            contentDescription = "view code",
                            tint = it.suitableColor()
                        )
                    }, onClick = {
                        context.sendEmail(email = Link.MAIL)
                    }
                )
                // version
                TextForThisTheme(
                    modifier = Modifier
                        .padding(10.dp)
                        .padding(bottom = 20.dp)
                        .align(Alignment.End),
                    fontSize = FontSize.small17,
                    text = "${stringResource(R.string.version)} ${LocalContext.current.getVersionName()}"
                )
            }
        }
    }
}