package com.example.githubapp.presentation.main.auth

import androidx.lifecycle.LiveData

interface AuthLogic {
    fun onCodeAccquired(code: String?)
    fun observeCallIntent(): LiveData<Unit>
}
