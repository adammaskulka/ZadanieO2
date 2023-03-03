package com.maskulka.zadanieo2.ui.scratch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maskulka.zadanieo2.managers.ScratchCardManager
import com.maskulka.zadanieo2.model.CardState
import com.maskulka.zadanieo2.repository.ScratchCardRepository
import com.maskulka.zadanieo2.utils.EMPTY_STRING
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber

class ScratchViewModel(
    private val repository: ScratchCardRepository,
) : ViewModel() {

    private val _loadingState = MutableStateFlow(State.INIT.ordinal)
    val loadingState: StateFlow<Int> = _loadingState

    val code = MutableStateFlow(EMPTY_STRING)

    fun onScratchButtonClick() {
        viewModelScope.launch {
            repository.scratchCard()
                .onStart { _loadingState.value = State.LOADING.ordinal }
                .onCompletion { Timber.d("onScratchButtonClick(): scratchCard() -> DONE") }
                .collect { result ->
                    result.onSuccess {
                        code.value = it
                        _loadingState.value = State.SUCCESS.ordinal
                    }
                }
        }
    }

    private enum class State {
        INIT,
        LOADING,
        SUCCESS
    }
}