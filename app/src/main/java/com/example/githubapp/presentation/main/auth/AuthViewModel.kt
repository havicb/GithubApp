package com.example.githubapp.presentation.main.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.githubapp.core.Failure
import com.example.githubapp.core.toView
import com.example.githubapp.domain.entity.TokenData
import com.example.githubapp.domain.entity.User
import com.example.githubapp.domain.usecase.GetAccessTokenUseCase
import com.example.githubapp.domain.usecase.GetLoggedInUserUseCase
import com.example.githubapp.domain.usecase.Params
import com.example.githubapp.domain.usecase.TokenParam
import com.example.githubapp.presentation.base.BaseViewModel
import com.example.githubapp.presentation.base.view.SingleLiveEvent
import kotlinx.coroutines.launch

class AuthViewModel(
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val getLoggedInUserUseCase: GetLoggedInUserUseCase
) : BaseViewModel(), AuthLogic {

    private val mCallIntent = SingleLiveEvent<Unit>()
    private val mUser = SingleLiveEvent<User?>()

    override fun observeNavigation(): LiveData<User?> = mUser
    override fun observeCallIntent(): LiveData<Unit> = mCallIntent

    override fun onSkipButton() {
        mUser.value = null
    }

    override fun onAuthButton() {
        mCallIntent.value = Unit
    }

    override fun onCodeAccquired(code: String?) {
        code?.let {
            fetchAccessToken(it)
        }
    }

    private fun fetchAccessToken(code: String) = viewModelScope.launch {
        getAccessTokenUseCase(Params(code)).fold(::handleFailure, ::handleAccessToken)
    }

    private fun handleAccessToken(tokenData: TokenData) = viewModelScope.launch {
        getLoggedInUserUseCase(TokenParam(tokenData.accessToken)).fold(
            ::handleFailure,
            ::handleUser
        )
    }

    private fun handleUser(user: User) {
        mUser.value = user
    }

    private fun handleFailure(failure: Failure) {
        // todo: Handle failure
    }
}
