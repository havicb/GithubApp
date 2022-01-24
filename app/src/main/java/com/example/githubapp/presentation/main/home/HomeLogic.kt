package com.example.githubapp.presentation.main.home

import androidx.lifecycle.LiveData
import com.example.githubapp.presentation.main.RepositoryView

interface HomeLogic {
    fun fetchData()
    fun observeGenericError(): LiveData<Unit>
    fun observeRepositoriesLoadingFailure(): LiveData<Unit>
    fun observeIsLoading(): LiveData<Boolean>
    fun observeShouldShowFilterDialog(): LiveData<Unit>
    fun observeRepositories(): LiveData<List<RepositoryView>>
    fun loadMore()
    fun onSortByStarsSelected()
    fun onSortByForksSelected()
    fun onSortByUpdatedSelected()
    fun onFilterIconClick()
    fun onSearchIconClick()
    fun onSearchRepositoriesTextChange(text: String)
}
