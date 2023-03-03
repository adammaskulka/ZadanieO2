package com.maskulka.zadanieo2.network.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CardActivationResponse(
    val android: Int
)