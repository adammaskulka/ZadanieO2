package com.maskulka.zadanieo2.repository

import com.maskulka.zadanieo2.managers.ScratchCardManager
import com.maskulka.zadanieo2.network.model.CardActivationResponse
import com.maskulka.zadanieo2.network.model.NetworkResult
import com.maskulka.zadanieo2.network.ScratchCardApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.util.UUID

class ScratchCardRepository(
    private val api: ScratchCardApi,
    private val manager: ScratchCardManager,
    private val applicationScope: CoroutineScope,
    private val ioScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) {

    fun observeScratchState() = manager.scratchState

    private val _cardActivationState = MutableStateFlow<NetworkResult<Unit>>(NetworkResult.Init)
    val cardActivationState: StateFlow<NetworkResult<Unit>> = _cardActivationState

    suspend fun scratchCard(): Flow<Result<String>> = flow {
        val code = withContext(ioScope.coroutineContext) {
            delay(3000L)
            UUID.randomUUID().toString()
        }

        manager.scratchCard(code)
        emit(Result.success(code))
    }

    suspend fun activateCard(): Flow<Result<CardActivationResponse>> = flow {
        withContext(applicationScope.coroutineContext) {
            _cardActivationState.value = NetworkResult.Loading
//            delay(10000L)

            val networkResponse = api.activateCard(manager.cardCode)

            if (networkResponse.isSuccess) {
                val result = networkResponse.getOrThrow()
                manager.activateCard(result.android,
                    onActivationSuccess = {
                        _cardActivationState.value = NetworkResult.Success()
                    }, onActivationError = {
                        _cardActivationState.value = NetworkResult.Error()
                    })
            } else {
                _cardActivationState.value = NetworkResult.Error()
            }
            emit(networkResponse)
        }
    }.catch {
        _cardActivationState.value = NetworkResult.Error()
    }

    fun resetCardState() {
        manager.resetCardState()
        _cardActivationState.value = NetworkResult.Init
    }
}
