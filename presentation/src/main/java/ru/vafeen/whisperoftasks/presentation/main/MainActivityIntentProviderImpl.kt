package ru.vafeen.whisperoftasks.presentation.main

import android.content.Context
import android.content.Intent
import ru.vafeen.whisperoftasks.domain.MainActivityIntentProvider

/**
 * Реализация интерфейса [MainActivityIntentProvider], предоставляющая Intent для запуска MainActivity.
 *
 * Этот класс предоставляет метод для создания объекта [Intent], который используется для запуска
 * главной активности приложения.
 *
 * @param context Контекст приложения, который необходим для создания [Intent].
 */
internal class MainActivityIntentProviderImpl(private val context: Context) : MainActivityIntentProvider {

    /**
     * Создание [Intent] для запуска главной активности [MainActivity].
     *
     * Функция возвращает новый объект [Intent], который может быть использован для старта [MainActivity].
     *
     * @return [Intent], который может быть использован для запуска [MainActivity].
     */
    override fun provideIntent(): Intent = Intent(context, MainActivity::class.java)
}
