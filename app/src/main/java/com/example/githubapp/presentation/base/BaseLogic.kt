package com.example.githubapp.presentation.base

import androidx.lifecycle.LiveData
import com.example.githubapp.presentation.base.view.NavigationEvent

interface BaseLogic {
    fun observeNavigationEvent(): LiveData<NavigationEvent>
}
