package ru.vafeen.whisperoftasks.domain.network.result

sealed class ResponseResult<T> {
    class Success<T>(val data: T) : ResponseResult<T>()
    class Error<T>(val exception: Exception) : ResponseResult<T>()
}