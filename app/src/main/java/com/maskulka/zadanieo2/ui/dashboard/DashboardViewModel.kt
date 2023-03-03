package com.maskulka.zadanieo2.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.maskulka.zadanieo2.R
import com.maskulka.zadanieo2.model.CardState
import com.maskulka.zadanieo2.repository.ScratchCardRepository
import com.maskulka.zadanieo2.utils.toStateFlow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val repository: ScratchCardRepository
) : ViewModel() {

    private val _navDirections: MutableSharedFlow<NavDirections> = MutableSharedFlow()
    val navDirections: SharedFlow<NavDirections> = _navDirections

    val state = repository.observeScratchState().map { cardState ->
        when (cardState) {
            CardState.New -> R.string.state_new
            CardState.Scratched -> R.string.state_scratched
            CardState.Activated -> R.string.state_activated
        }
    }.toStateFlow(initialValue = R.string.state_new)

    val isScratchButtonEnabled = repository.observeScratchState().map { cardState -> cardState is CardState.New }.toStateFlow(initialValue = false)
    val isActivateButtonEnabled = repository.observeScratchState().map { cardState -> cardState is CardState.Scratched }.toStateFlow(initialValue = false)
    val isResetButtonVisible = repository.observeScratchState().map { cardState -> cardState is CardState.Activated }.toStateFlow(initialValue = false)

    fun onScratchButtonClick() {
        viewModelScope.launch {
            _navDirections.emit(DashboardFragmentDirections.navigateToScratchFragment())
        }
    }

    fun onActivateButtonClick() {
        viewModelScope.launch {
            _navDirections.emit(DashboardFragmentDirections.navigateToActivationFragment())
        }
    }

    fun onResetButtonClick() {
        repository.resetCardState()
    }

}