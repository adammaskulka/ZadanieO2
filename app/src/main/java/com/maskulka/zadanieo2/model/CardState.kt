package com.maskulka.zadanieo2.model

sealed class CardState {
    object New : CardState()
    object Scratched : CardState()
    object Activated : CardState()
}
