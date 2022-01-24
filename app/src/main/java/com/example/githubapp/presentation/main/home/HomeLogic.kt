package com.example.githubapp.presentation.main.home

import androidx.lifecycle.LiveData
import com.example.githubapp.presentation.main.RepositoryView

interface HomeLogic {
    val observeGenericError: LiveData<Unit>
    val observeRepositoriesFailedLoading: LiveData<Unit>
    val observeLoading: LiveData<Boolean>
    val observeShouldShowFilterDialog: LiveData<Unit>
    val observeRepositories: LiveData<List<RepositoryView>>

    fun fetchData()
    fun loadMore()
    fun onSortByStarsSelected()
    fun onSortByForksSelected()
    fun onSortByUpdatedSelected()
    fun onFilterIconClick()
    fun onSearchIconClick()
    fun onSearchRepositoriesTextChange(text: String)
}
