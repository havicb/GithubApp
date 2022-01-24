package com.example.githubapp.presentation.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import com.example.githubapp.presentation.base.view.NavigationEvent

interface BaseLogic {
    val observeNavigationEvent: LiveData<NavigationEvent>
}
