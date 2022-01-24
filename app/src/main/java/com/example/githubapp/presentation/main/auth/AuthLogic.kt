package com.example.githubapp.presentation.main.auth

import androidx.lifecycle.LiveData
import com.example.githubapp.presentation.base.view.NavigationEvent

interface AuthLogic {
    val observeNavigation: LiveData<NavigationEvent>
    val observeIsLoading: LiveData<Boolean>
    val observeShouldCallIntent: LiveData<Unit>

    fun onCodeAccquired(code: String?)
    fun onSkipButton()
    fun onAuthButton()
}
