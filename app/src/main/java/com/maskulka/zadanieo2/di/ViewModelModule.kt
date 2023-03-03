package com.maskulka.zadanieo2.di

import com.maskulka.zadanieo2.ui.activation.ActivationViewModel
import com.maskulka.zadanieo2.ui.dashboard.DashboardViewModel
import com.maskulka.zadanieo2.ui.scratch.ScratchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {

    viewModelOf(::DashboardViewModel)
    viewModelOf(::ActivationViewModel)
    viewModelOf(::ScratchViewModel)

}