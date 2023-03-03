package com.maskulka.zadanieo2.application

import android.app.Application
import com.maskulka.zadanieo2.di.appModule
import com.maskulka.zadanieo2.di.networkModule
import com.maskulka.zadanieo2.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(listOf(appModule, networkModule, viewModelModule))
        }
    }

    companion object {
        const val BASE_URL = "https://api.o2.sk/"
        const val CARD_ACTIVATION_SUCCESS_CODE = 80_000
    }

}