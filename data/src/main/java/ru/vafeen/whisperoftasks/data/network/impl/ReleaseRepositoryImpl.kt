package ru.vafeen.whisperoftasks.data.network.impl

import ru.vafeen.whisperoftasks.data.converters.ReleaseConverter
import ru.vafeen.whisperoftasks.data.network.service.GitHubDataService
import ru.vafeen.whisperoftasks.domain.models.Release
import ru.vafeen.whisperoftasks.domain.network.ReleaseRepository
import ru.vafeen.whisperoftasks.domain.network.result.ResponseResult
import java.io.IOException
import java.net.UnknownHostException

internal class ReleaseRepositoryImpl(
    private val gitHubDataService: GitHubDataService,
    private val releaseConverter: ReleaseConverter
) : ReleaseRepository {

    override suspend fun getLatestRelease(): ResponseResult<Release> {
        return try {
            // Выполнение запроса
            val response = gitHubDataService.getLatestRelease()
            val body = response.body()
            // Проверка на успешный ответ (например, статус 200)
            if (response.isSuccessful && body != null) {
                // Возвращаем успешный результат
                ResponseResult.Success(releaseConverter.convertAB(body))
            } else {
                // Обработка HTTP ошибки, если статус не успешен
                ResponseResult.Error(Exception("Ошибка сервера: ${response.code()}"))
            }
        } catch (e: UnknownHostException) {
            // Ошибка при отсутствии интернета
            ResponseResult.Error(UnknownHostException("Нет подключения к интернету"))
        } catch (e: IOException) {
            // Общая ошибка сети
            ResponseResult.Error(IOException("Ошибка сети: ${e.localizedMessage}"))
        } catch (e: Exception) {
            // Обработка любых других ошибок
            ResponseResult.Error(Exception("Неизвестная ошибка: ${e.localizedMessage}"))
        }
    }
}
