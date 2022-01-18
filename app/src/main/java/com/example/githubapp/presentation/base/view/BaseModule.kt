package com.example.githubapp.presentation.base.view

import com.example.githubapp.presentation.base.BaseLogic
import com.example.githubapp.presentation.base.BaseViewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val baseModule = module {
    single { BaseViewModel() } bind BaseLogic::class
}
