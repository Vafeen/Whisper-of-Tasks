package ru.vafeen.whisperoftasks.domain.network

import ru.vafeen.whisperoftasks.domain.models.Release
import ru.vafeen.whisperoftasks.domain.network.result.ResponseResult

interface ReleaseRepository {
    suspend fun getLatestRelease(): ResponseResult<Release>
}