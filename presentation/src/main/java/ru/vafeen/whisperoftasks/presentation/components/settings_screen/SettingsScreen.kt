package ru.vafeen.whisperoftasks.presentation.components.settings_screen

import androidx.activity.compose.BackHandler
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
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
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
import org.koin.androidx.compose.koinViewModel
import ru.vafeen.whisperoftasks.domain.utils.Link
import ru.vafeen.whisperoftasks.domain.utils.getMainColorForThisTheme
import ru.vafeen.whisperoftasks.domain.utils.getVersionName
import ru.vafeen.whisperoftasks.domain.utils.openLink
import ru.vafeen.whisperoftasks.domain.utils.sendEmail
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.CardOfSettings
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.ColorPickerDialog
import ru.vafeen.whisperoftasks.presentation.common.components.ui_utils.TextForThisTheme
import ru.vafeen.whisperoftasks.presentation.components.navigation.BottomBarNavigator
import ru.vafeen.whisperoftasks.presentation.components.navigation.Screen
import ru.vafeen.whisperoftasks.presentation.ui.theme.FontSize
import ru.vafeen.whisperoftasks.presentation.ui.theme.Theme
import ru.vafeen.whisperoftasks.presentation.utils.suitableColor
import ru.vafeen.whisperoftasks.resources.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SettingsScreen(bottomBarNavigator: BottomBarNavigator) {
    val viewModel: SettingsScreenViewModel = koinViewModel()
    val context = LocalContext.current
    val dark = isSystemInDarkTheme()
    val mainDefaultColor = Theme.colors.mainColor
    val settings by viewModel.settings.collectAsState()
    val mainColor by remember {
        derivedStateOf {
            settings.getMainColorForThisTheme(isDark = dark) ?: mainDefaultColor
        }
    }
    var colorIsEditable by remember {
        mutableStateOf(false)
    }

    BackHandler(onBack = bottomBarNavigator::back)

    Scaffold(
        containerColor = Theme.colors.background,
        modifier = Modifier.fillMaxSize(),
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

        ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
        ) {

            if (colorIsEditable) ColorPickerDialog(context = context,
                firstColor = mainColor,
                onDismissRequest = { colorIsEditable = false }) {
                viewModel.saveSettings { s ->
                    if (dark) s.copy(darkThemeColor = it) else s.copy(lightThemeColor = it)
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 20.dp)
                    .verticalScroll(rememberScrollState()),
            ) {
                CardOfSettings(text = stringResource(R.string.trash_bin), icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.delete_forever),
                        contentDescription = stringResource(R.string.trash_bin),
                        tint = it.suitableColor()
                    )
                }, onClick = { bottomBarNavigator.navigateTo(Screen.TrashBin) })

                CardOfSettings(text = stringResource(R.string.reminder_recovery), icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.update),
                        contentDescription = stringResource(R.string.trash_bin),
                        tint = it.suitableColor()
                    )
                }, onClick = {
                    viewModel.recoveryReminders(context)
                })

                Box(modifier = Modifier.fillMaxWidth()) {
                    // name of section
                    TextForThisTheme(
                        modifier = Modifier
                            .padding(10.dp)
                            .align(Alignment.Center),
                        fontSize = FontSize.big22,
                        text = stringResource(R.string.interface_str)
                    )
                }


                // Color
                CardOfSettings(text = stringResource(R.string.interface_color), icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.palette),
                        contentDescription = "change color of interface",
                        tint = it.suitableColor()
                    )
                }, onClick = { colorIsEditable = true })


                // name of section
                TextForThisTheme(
                    modifier = Modifier
                        .padding(10.dp)
                        .align(Alignment.CenterHorizontally),
                    fontSize = FontSize.big22,
                    text = stringResource(R.string.contacts)
                )

                // CODE
                CardOfSettings(text = stringResource(R.string.code), icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.terminal),
                        contentDescription = "view code",
                        tint = it.suitableColor()
                    )
                }, onClick = { context.openLink(link = Link.CODE) })

                CardOfSettings(text = stringResource(R.string.report_a_bug), icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.bug_report),
                        contentDescription = "view code",
                        tint = it.suitableColor()
                    )
                }, onClick = { context.sendEmail(email = Link.MAIL) })

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