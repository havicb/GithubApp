package com.example.githubapp.presentation.main.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.githubapp.core.Failure
import com.example.githubapp.domain.usecase.GetAccessTokenUseCase
import com.example.githubapp.domain.usecase.Params
import com.example.githubapp.domain.usecase.UserData
import com.example.githubapp.presentation.base.BaseViewModel
import com.example.githubapp.presentation.base.view.SingleLiveEvent
import kotlinx.coroutines.launch

class AuthViewModel(
    private val getAccessTokenUseCase: GetAccessTokenUseCase
) : BaseViewModel(), AuthLogic {

    private val mCallIntent = SingleLiveEvent<Unit>()

    override fun observeCallIntent(): LiveData<Unit> = mCallIntent

    init {
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

    private fun handleAccessToken(userData: UserData) {
        Log.i("CALLING", "CALLED -> ${userData.accessToken}")
    }

    private fun handleFailure(failure: Failure) {
        // todo: Handle failure
    }
}
