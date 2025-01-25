package ru.vafeen.whisperoftasks.domain.network.repository

import ru.vafeen.whisperoftasks.domain.domain_models.Release
import ru.vafeen.whisperoftasks.domain.network.result.ResponseResult

interface ReleaseRemoteRepository {
    suspend fun getLatestRelease(): ResponseResult<Release>
}