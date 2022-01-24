package com.example.githubapp.presentation.main.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.githubapp.core.Failure
import com.example.githubapp.core.toView
import com.example.githubapp.domain.entity.TokenData
import com.example.githubapp.domain.entity.User
import com.example.githubapp.domain.entity.UserView
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
    private val mUser = SingleLiveEvent<UserView?>()
    private val mLoginLoading = MutableLiveData<Boolean>()

    private var mHasUserLoggedIn = false
    private var _user: User? = null

    override fun observeLoginLoading(): LiveData<Boolean> = mLoginLoading
    override fun observeNavigation(): LiveData<UserView?> = mUser
    override fun observeCallIntent(): LiveData<Unit> = mCallIntent

    override fun onSkipButton() {
        mUser.value = null
    }

    override fun onAuthButton() {
        if (mHasUserLoggedIn) {
            mUser.value = _user?.toView()
        } else {
            mCallIntent.value = Unit
        }
    }

    override fun onCodeAccquired(code: String?) {
        code?.let {
            if (!mHasUserLoggedIn) {
                fetchAccessToken(it)
            }
        }
    }

    private fun fetchAccessToken(code: String) = viewModelScope.launch {
        getAccessTokenUseCase(Params(code)).fold(::handleFailure, ::handleAccessToken)
    }

    private fun handleAccessToken(tokenData: TokenData) = viewModelScope.launch {
        mLoginLoading.value = true
        getLoggedInUserUseCase(TokenParam(tokenData.accessToken)).fold(
            ::handleFailure,
            ::handleUser
        )
        mLoginLoading.value = false
    }

    private fun handleUser(user: User) {
        mHasUserLoggedIn = true
        _user = user
        mUser.value = user.toView()
    }

    private fun handleFailure(failure: Failure) {
        mHasUserLoggedIn = false
        // todo: Handle failure
    }
}
