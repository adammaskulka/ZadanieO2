package com.maskulka.zadanieo2.network.model

sealed class NetworkResult<out R> {
    data class Success<out T>(val data: T? = null) : NetworkResult<T>()
    data class Error(val error: Throwable? = null) : NetworkResult<Nothing>()
    object Loading : NetworkResult<Nothing>()
    object Init : NetworkResult<Nothing>()
}