package com.example.githubapp.presentation.base.view

import androidx.navigation.NavDirections

sealed class NavigationEvent {
    data class To(val directions: NavDirections) : NavigationEvent()
    object Back : NavigationEvent()
}
