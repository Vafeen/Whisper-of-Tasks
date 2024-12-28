package ru.vafeen.whisperoftasks.data.network.impl

import ru.vafeen.whisperoftasks.data.network.service.DownloadService
import ru.vafeen.whisperoftasks.domain.models.CompositeFileStream
import ru.vafeen.whisperoftasks.domain.network.DownloadFileRepository
import ru.vafeen.whisperoftasks.domain.network.result.ResponseResult
import java.io.IOException
import java.net.UnknownHostException

internal class DownloadFileRepositoryImpl(
    private val downloadService: DownloadService
) : DownloadFileRepository {

    override suspend fun downloadFile(fileUrl: String): ResponseResult<CompositeFileStream> = try {
        // Выполнение запроса
        val call = downloadService.downloadFile(fileUrl)
        val response = call.execute() // Синхронный вызов
        val body = response.body()
        // Проверяем успешность ответа и наличие тела
        if (response.isSuccessful && body != null) {
            val contentLength = body.contentLength()
            // Возвращаем успешный результат
            ResponseResult.Success(
                CompositeFileStream(
                    stream = body.byteStream(), // Получаем InputStream из тела ответа
                    contentLength = contentLength
                )
            )
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



