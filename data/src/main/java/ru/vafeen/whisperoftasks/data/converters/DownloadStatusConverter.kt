package ru.vafeen.whisperoftasks.data.converters

import ru.vafeen.universityschedule.domain.converter.TwoWayBaseConverter
import ru.vafeen.whisperoftasks.domain.network.result.DownloadStatus
import android.vafeen.direct_refresher.downloader.DownloadStatus as LibDownloadStatus

/**
 * Конвертер для преобразования статуса загрузки между библиотечным типом и типом приложения.
 * Реализует двусторонний конвертер, но в настоящее время конвертация из DownloadStatus в LibDownloadStatus не поддерживается.
 */
internal class DownloadStatusConverter : TwoWayBaseConverter<LibDownloadStatus, DownloadStatus> {

    /**
     * Преобразует статус загрузки из библиотечного типа в тип приложения.
     *
     * @param a Статус загрузки из библиотеки.
     * @return Соответствующий статус загрузки для приложения.
     */
    override fun convertAB(a: LibDownloadStatus): DownloadStatus = when (a) {
        is LibDownloadStatus.Error -> DownloadStatus.Error(a.exception)
        is LibDownloadStatus.InProgress -> DownloadStatus.InProgress(a.percentage)
        LibDownloadStatus.Started -> DownloadStatus.Started
        LibDownloadStatus.Success -> DownloadStatus.Success
    }

    /**
     * Преобразует статус загрузки из типа приложения в библиотечный тип.
     * В настоящее время не поддерживается и выбрасывает исключение.
     *
     * @param b Статус загрузки из приложения.
     * @throws Exception Выбрасывается всегда, поскольку конвертация не реализована.
     */
    override fun convertBA(b: DownloadStatus): LibDownloadStatus =
        throw Exception("ru.vafeen.universityschedule.data.converters.DownloadStatusConverter is not defined")
}
