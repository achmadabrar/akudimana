package com.abrar.akudimana.core.di.module

import com.abrar.akudimana.ViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val AppModule = module {

    viewModel { ViewModel(get()) }
}
