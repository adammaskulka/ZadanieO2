package com.maskulka.zadanieo2.ui.activation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maskulka.zadanieo2.network.model.NetworkResult
import com.maskulka.zadanieo2.repository.ScratchCardRepository
import com.maskulka.zadanieo2.utils.toStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import timber.log.Timber

class ActivationViewModel(
    private val repository: ScratchCardRepository,
) : ViewModel() {

    val loadingState = repository.cardActivationState.map { state ->
        when (state) {
            NetworkResult.Init -> State.INIT
            NetworkResult.Loading -> State.LOADING
            is NetworkResult.Success -> State.SUCCESS
            is NetworkResult.Error -> State.ERROR
        }.ordinal
    }.toStateFlow(initialValue = State.INIT.ordinal)

    private fun activateCard() {
        viewModelScope.launch {
            repository.activateCard()
                .onCompletion { Timber.d("activateCard() -> DONE") }
                .collect { result ->
                    result.onSuccess {
                        Timber.d("activateCard(): onSuccess -> ${it.android}")
                    }
                }
        }
    }

    fun onActivateButtonClick() {
        activateCard()
    }

    fun onRetryButtonClick() {
        activateCard()
    }

    private enum class State {
        INIT,
        LOADING,
        SUCCESS,
        ERROR
    }

}