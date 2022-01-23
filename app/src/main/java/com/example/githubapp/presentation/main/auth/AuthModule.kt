package com.example.githubapp.presentation.main.auth

import org.koin.dsl.bind
import org.koin.dsl.module

val authModule = module {
    single { AuthViewModel(get()) } bind AuthLogic::class
}