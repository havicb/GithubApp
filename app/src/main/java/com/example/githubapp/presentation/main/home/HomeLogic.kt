package com.example.githubapp.presentation.main.home

import androidx.lifecycle.LiveData
import com.example.githubapp.presentation.main.RepositoryView

interface HomeLogic {
    fun observeIsLoading(): LiveData<Boolean>
    fun observeShouldShowFilterDialog(): LiveData<Unit>
    fun observeRepositories(): LiveData<List<RepositoryView>>
    fun onSortByStarsSelected()
    fun onSortByForksSelected()
    fun onSortByUpdatedSelected()
    fun onFilterIconClick()
    fun onSearchIconClick()
    fun onSearchRepositoriesTextChange(text: String)
}
