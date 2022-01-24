package com.example.githubapp.presentation.main.error

import androidx.lifecycle.LiveData
import com.example.githubapp.presentation.base.view.NavigationEvent

interface ErrorLogic {
    val observeNavigation: LiveData<NavigationEvent>

    fun onRetryButton()
}
