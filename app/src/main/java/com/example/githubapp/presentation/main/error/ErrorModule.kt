package com.example.githubapp.presentation.main.error

import org.koin.dsl.bind
import org.koin.dsl.module

val errorModule = module {
    single { ErrorViewModel() } bind ErrorLogic::class
}
