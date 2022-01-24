package com.example.githubapp.presentation.main.auth

import androidx.lifecycle.LiveData
import com.example.githubapp.domain.entity.UserView

interface AuthLogic {
    fun observeLoginLoading(): LiveData<Boolean>
    fun observeNavigation(): LiveData<UserView?>
    fun observeCallIntent(): LiveData<Unit>
    fun onCodeAccquired(code: String?)
    fun onSkipButton()
    fun onAuthButton()
}
