package com.example.githubapp.presentation.base

import org.koin.dsl.bind
import org.koin.dsl.module

val baseModule = module {
    single { BaseViewModel() } bind BaseLogic::class
}
