package ru.vafeen.whisperoftasks.data.utils

import ru.vafeen.whisperoftasks.domain.network.result.ResponseResult
import java.io.IOException
import java.net.UnknownHostException

/**
 * Функция для обработки ошибок при выполнении сетевых запросов.
 * Обрабатывает исключения и возвращает результат запроса в виде [ResponseResult].
 *
 * @param response Функция, выполняющая сетевой запрос и возвращающая [ResponseResult].
 * @return Результат запроса, содержащий данные или информацию об ошибке.
 */
internal suspend fun <T> getResponseWrappedAllErrors(response: suspend () -> ResponseResult<T>): ResponseResult<T> =
    try {
        // Выполняем сетевой запрос
        response()
    } catch (e: UnknownHostException) {
        // Обработка ошибки отсутствия подключения к интернету
        ResponseResult.Error(UnknownHostException("Нет подключения к интернету"))
    } catch (e: IOException) {
        // Обработка сетевой ошибки
        ResponseResult.Error(IOException("Ошибка сети: ${e.localizedMessage}"))
    } catch (e: Exception) {
        // Обработка других исключений
        ResponseResult.Error(Exception("Неизвестная ошибка: ${e.localizedMessage}"))
    }