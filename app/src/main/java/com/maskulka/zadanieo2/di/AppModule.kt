package com.maskulka.zadanieo2.di

import com.maskulka.zadanieo2.managers.NotificationManager
import com.maskulka.zadanieo2.managers.ScratchCardManager
import com.maskulka.zadanieo2.repository.ScratchCardRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.plus
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {

    val applicationScope = CoroutineScope(Dispatchers.IO) + SupervisorJob()

    single { applicationScope }
    singleOf(::ScratchCardManager)
    singleOf(::NotificationManager)
    singleOf(::ScratchCardRepository)

}