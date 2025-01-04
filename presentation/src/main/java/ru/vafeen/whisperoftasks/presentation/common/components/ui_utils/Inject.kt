package ru.vafeen.whisperoftasks.presentation.common.components.ui_utils

import androidx.compose.runtime.Composable
import org.koin.core.context.GlobalContext
import org.koin.compose.getKoin as KoinCompose

@Composable
inline fun <reified T : Any> InjectCompose(): T = KoinCompose().get()
inline fun <reified T : Any> Inject(): T = GlobalContext.get().get<T>()
