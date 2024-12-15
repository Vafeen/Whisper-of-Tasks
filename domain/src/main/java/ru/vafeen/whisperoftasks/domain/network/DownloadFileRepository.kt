package ru.vafeen.whisperoftasks.domain.network


import ru.vafeen.whisperoftasks.domain.models.CompositeFileStream
import ru.vafeen.whisperoftasks.domain.network.result.ResponseResult


interface DownloadFileRepository {
    suspend fun downloadFile(fileUrl: String): ResponseResult<CompositeFileStream>
}