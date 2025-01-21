package ru.vafeen.whisperoftasks.data.network.repository

import ru.vafeen.whisperoftasks.data.converters.ReleaseConverter
import ru.vafeen.whisperoftasks.data.network.service.ReleaseRemoteService
import ru.vafeen.whisperoftasks.data.utils.getResponseWrappedAllErrors
import ru.vafeen.whisperoftasks.domain.domain_models.Release
import ru.vafeen.whisperoftasks.domain.network.repository.ReleaseRemoteRepository
import ru.vafeen.whisperoftasks.domain.network.result.ResponseResult

/**
 * Реализация репозитория для получения информации о релизах из GitHub.
 *
 * @property releaseRemoteService Сервис для выполнения запросов к API GitHub.
 * @property releaseConverter Конвертер для преобразования объектов Release между слоями.
 */
internal class ReleaseRemoteRepositoryImpl(
    private val releaseRemoteService: ReleaseRemoteService,
    private val releaseConverter: ReleaseConverter
) : ReleaseRemoteRepository {

    /**
     * Получение последнего релиза из GitHub.
     *
     * @return Результат запроса, содержащий информацию о релизе или ошибку.
     */
    override suspend fun getLatestRelease(): ResponseResult<Release> =
        getResponseWrappedAllErrors {
            // Выполнение запроса
            val response = releaseRemoteService.getLatestRelease()
            val release = releaseConverter.convertAB(response.body())
            // Проверка на успешный ответ и релиза на null после конвертации
            if (response.isSuccessful && release != null) {
                // Возвращаем успешный результат
                ResponseResult.Success(release)
            } else {
                // Обработка HTTP ошибки, если статус не успешен
                ResponseResult.Error(Exception("Ошибка сервера: ${response.code()}"))
            }
        }
}