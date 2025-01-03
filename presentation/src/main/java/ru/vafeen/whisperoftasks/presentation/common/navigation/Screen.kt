package ru.vafeen.whisperoftasks.presentation.common.navigation

import kotlinx.serialization.Serializable

internal sealed class Screen {
    @Serializable
    data object MainScreen

    @Serializable
    data object RemindersScreen
}