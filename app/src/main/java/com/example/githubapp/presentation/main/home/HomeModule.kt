package com.example.githubapp.presentation.main.home

import org.koin.dsl.bind
import org.koin.dsl.module

val homeModule = module {
    single { HomeViewModel(get()) } bind HomeLogic::class
}
