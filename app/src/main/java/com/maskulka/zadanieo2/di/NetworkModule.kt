package com.maskulka.zadanieo2.di

import com.maskulka.zadanieo2.application.MyApplication.Companion.BASE_URL
import com.maskulka.zadanieo2.network.ScratchCardApi
import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {
    factory { provideOkHttpClient() }
    factory { provideRetrofitApi(get()) }
    single { provideMoshi() }
    single { provideRetrofit(get(), get()) }
}

fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(ResultCallAdapterFactory.create())
        .build()
}

fun provideMoshi(): Moshi {
    return Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
}

fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient()
        .newBuilder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()
}

fun provideRetrofitApi(retrofit: Retrofit): ScratchCardApi = retrofit.create(ScratchCardApi::class.java)