package ru.vafeen.whisperoftasks.data.converters

import ru.vafeen.whisperoftasks.domain.network.result.DownloadStatus
import android.vafeen.direct_refresher.downloader.DownloadStatus as LibDownloadStatus

/**
 * Конвертер для преобразования статуса загрузки между библиотечным типом и типом приложения.
 */
internal fun LibDownloadStatus.toDownloadStatus(): DownloadStatus = when (this) {
    is LibDownloadStatus.Error -> DownloadStatus.Error(this.exception)
    is LibDownloadStatus.InProgress -> DownloadStatus.InProgress(this.percentage)
    LibDownloadStatus.Started -> DownloadStatus.Started
    LibDownloadStatus.Success -> DownloadStatus.Success
}
