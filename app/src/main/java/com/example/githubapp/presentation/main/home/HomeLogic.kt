package com.example.githubapp.presentation.main.home

import androidx.lifecycle.LiveData
import com.example.githubapp.presentation.base.view.NavigationEvent
import com.example.githubapp.presentation.main.OwnerView
import com.example.githubapp.presentation.main.RepositoryView

interface HomeLogic {
    val observeNavigation: LiveData<NavigationEvent>
    val observeGenericError: LiveData<Unit>
    val observeLoading: LiveData<Boolean>
    val observeShouldShowFilterDialog: LiveData<Unit>
    val observeRepositories: LiveData<List<RepositoryView>>

    fun onRepositoryClick(repositoryView: RepositoryView)
    fun onAvatarClick(ownerView: OwnerView)
    fun fetchData()
    fun loadMore()
    fun onSortByStarsSelected()
    fun onSortByForksSelected()
    fun onSortByUpdatedSelected()
    fun onFilterIconClick()
    fun onSearchIconClick()
    fun onSearchRepositoriesTextChange(text: String)
}
