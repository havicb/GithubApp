package com.example.githubapp.presentation.main.auth

import androidx.lifecycle.LiveData
import com.example.githubapp.domain.entity.UserView

interface AuthLogic {
    val observeIsLoading: LiveData<Boolean>
    val observeShouldNavigate: LiveData<UserView?>
    val observeShouldCallIntent: LiveData<Unit>

    fun onCodeAccquired(code: String?)
    fun onSkipButton()
    fun onAuthButton()
}
