package ru.vafeen.whisperoftasks.domain.shared_preferences

import kotlinx.coroutines.flow.StateFlow
import ru.vafeen.whisperoftasks.domain.domain_models.Settings

/**
 * Интерфейс для управления настройками приложения.
 */
interface SettingsManager {

    /**
     * Поток состояний, представляющий текущие настройки приложения.
     */
    val settingsFlow: StateFlow<Settings>

    /**
     * Сохраняет изменения в настройках.
     *
     * @param saving Функция, принимающая текущие настройки и возвращающая измененные настройки.
     */
    fun save(saving: (Settings) -> Settings)
}
