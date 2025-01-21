package ru.vafeen.whisperoftasks.domain.network.usecase

import ru.vafeen.whisperoftasks.domain.domain_models.Release
import ru.vafeen.whisperoftasks.domain.network.repository.ReleaseRemoteRepository
import ru.vafeen.whisperoftasks.domain.network.result.ResponseResult

/**
 * UseCase для получения последнего релиза.
 *
 * Этот класс отвечает за выполнение операции получения последнего релиза из репозитория.
 *
 * @property releaseRemoteRepository Репозиторий, используемый для взаимодействия с сетевыми запросами на получение релизов.
 */
class GetLatestReleaseUseCase(private val releaseRemoteRepository: ReleaseRemoteRepository) {

    /**
     * Получает последний релиз.
     *
     * @return Объект [Release] последнего релиза или null, если произошла ошибка при получении данных.
     */
    suspend fun invoke(): Release? =
        releaseRemoteRepository.getLatestRelease().let {
            when (it) {
                is ResponseResult.Success -> it.data // Возвращаем данные в случае успеха.
                is ResponseResult.Error -> null // Возвращаем null в случае ошибки.
            }
        }
}
