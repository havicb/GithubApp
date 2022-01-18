package com.example.githubapp.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.example.githubapp.presentation.base.view.NavigationEvent
import com.example.githubapp.presentation.base.view.SingleLiveEvent

open class BaseViewModel() : ViewModel(), BaseLogic {

    private val navigationEvent = SingleLiveEvent<NavigationEvent>()

    override fun observeNavigationEvent(): LiveData<NavigationEvent> = navigationEvent

    protected fun navigate(navEvent: NavigationEvent) {
        navigationEvent.value = navEvent
    }

    protected fun navigate(navDirections: NavDirections) {
        navigationEvent.value = NavigationEvent.To(navDirections)
    }
}
