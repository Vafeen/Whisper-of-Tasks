package ru.vafeen.whisperoftasks.presentation.components.trash_bin_screen

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

internal val TrashBinScreenModule = module {
    viewModelOf(::TrashBinScreenViewModel)
}