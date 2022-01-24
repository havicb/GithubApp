package com.example.githubapp.presentation.main.auth

import androidx.lifecycle.LiveData
import com.example.githubapp.domain.entity.User

interface AuthLogic {
    fun observeNavigation(): LiveData<User?>
    fun observeCallIntent(): LiveData<Unit>
    fun onCodeAccquired(code: String?)
    fun onSkipButton()
    fun onAuthButton()
}
