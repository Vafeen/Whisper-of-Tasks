package ru.vafeen.whisperoftasks.presentation.components.navigation

import kotlinx.serialization.Serializable

internal sealed class Screen {
    @Serializable
    data object Main : Screen()

    @Serializable
    data object Reminders : Screen()

    @Serializable
    data object Settings : Screen()

    @Serializable
    data object TrashBin : Screen()
}