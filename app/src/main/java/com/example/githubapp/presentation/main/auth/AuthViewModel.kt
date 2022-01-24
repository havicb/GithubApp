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

    private val shouldCallIntent = SingleLiveEvent<Unit>()
    private val shouldNavigate = SingleLiveEvent<UserView?>()
    private val isLoading = MutableLiveData<Boolean>()

    private var hasUserLoggedIn = false
    private var currentUser: User? = null

    override val observeIsLoading: LiveData<Boolean>
        get() = isLoading
    override val observeShouldNavigate: LiveData<UserView?>
        get() = shouldNavigate
    override val observeShouldCallIntent: LiveData<Unit>
        get() = shouldCallIntent

    override fun onSkipButton() {
        shouldNavigate.value = null
    }

    override fun onAuthButton() {
        if (hasUserLoggedIn) {
            shouldNavigate.value = currentUser?.toView()
        } else {
            shouldCallIntent.value = Unit
        }
    }

    override fun onCodeAccquired(code: String?) {
        if (!hasUserLoggedIn && code != null) {
            fetchAccessToken(code)
        }
    }

    private fun fetchAccessToken(code: String) = viewModelScope.launch {
        getAccessTokenUseCase(Params(code)).fold(::handleFailure, ::handleAccessToken)
    }

    private fun handleAccessToken(tokenData: TokenData) = viewModelScope.launch {
        isLoading.value = true
        getLoggedInUserUseCase(TokenParam(tokenData.accessToken)).fold(
            ::handleFailure,
            ::handleUser
        )
        isLoading.value = false
    }

    private fun handleUser(user: User) {
        hasUserLoggedIn = true
        currentUser = user
        this.shouldNavigate.value = user.toView()
    }

    private fun handleFailure(failure: Failure) {
        hasUserLoggedIn = false
        // todo: Handle failure
    }
}
