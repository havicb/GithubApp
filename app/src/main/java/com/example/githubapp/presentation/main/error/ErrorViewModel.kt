package com.example.githubapp.presentation.main.error

import androidx.lifecycle.LiveData
import com.example.githubapp.presentation.base.BaseViewModel
import com.example.githubapp.presentation.base.view.NavigationEvent
import com.example.githubapp.presentation.base.view.SingleLiveEvent

class ErrorViewModel : BaseViewModel(), ErrorLogic {

    private val navEvent = SingleLiveEvent<NavigationEvent>()

    override val observeNavigation: LiveData<NavigationEvent> = navEvent

    override fun onRetryButton() {
        navEvent.value = NavigationEvent.Back
    }
}
