package com.maskulka.zadanieo2.managers

import android.app.Application
import com.maskulka.zadanieo2.R
import com.maskulka.zadanieo2.application.MyApplication.Companion.CARD_ACTIVATION_SUCCESS_CODE
import com.maskulka.zadanieo2.model.CardState
import com.maskulka.zadanieo2.utils.EMPTY_STRING
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ScratchCardManager(
    private val application: Application,
    private val notificationManager: NotificationManager
) {

    private val _scratchState = MutableStateFlow<CardState>(CardState.New)
    val scratchState: StateFlow<CardState> = _scratchState

    var cardCode: String = EMPTY_STRING
        private set

    fun resetCardState() {
        _scratchState.value = CardState.New
    }

    fun scratchCard(code: String) {
        _scratchState.value = CardState.Scratched
        cardCode = code
    }

    fun activateCard(responseCode: Int, onActivationSuccess: (Int) -> Unit, onActivationError: () -> Unit) {
        if (responseCode > CARD_ACTIVATION_SUCCESS_CODE) {
            _scratchState.value = CardState.Activated
            onActivationSuccess.invoke(responseCode)
        } else {
            notificationManager.showNotification(
                application.getString(R.string.card_activation_error_title),
                application.getString(R.string.card_activation_error_desc, responseCode)
            )
            onActivationError.invoke()
        }
    }
}