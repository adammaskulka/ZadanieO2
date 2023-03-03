package com.maskulka.zadanieo2.network

import com.maskulka.zadanieo2.network.model.CardActivationResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ScratchCardApi {

    @GET("version")
    suspend fun activateCard(@Query("code") code: String): Result<CardActivationResponse>

}