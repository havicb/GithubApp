package com.example.githubapp.presentation.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.githubapp.core.Failure
import com.example.githubapp.core.enums.RepositorySortType
import com.example.githubapp.core.toView
import com.example.githubapp.domain.entity.Repositories
import com.example.githubapp.domain.entity.RepositoryEntity
import com.example.githubapp.domain.repositories.GetRepositoriesUseCase
import com.example.githubapp.domain.repositories.Params
import com.example.githubapp.presentation.base.BaseViewModel
import com.example.githubapp.presentation.base.view.SingleLiveEvent
import com.example.githubapp.presentation.main.RepositoryView
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getRepositoriesUseCase: GetRepositoriesUseCase
) : BaseViewModel(), HomeLogic {

    private val mRepositories = MutableLiveData<List<RepositoryView>>()
    private val mShouldShowFilterDialog = SingleLiveEvent<Unit>()
    private val mIsLoading = MutableLiveData<Boolean>()

    private val _mRepositories = arrayListOf<RepositoryEntity>()
    private var mSearchTerm = ""

    override fun observeIsLoading(): LiveData<Boolean> = mIsLoading
    override fun observeShouldShowFilterDialog(): LiveData<Unit> = mShouldShowFilterDialog
    override fun observeRepositories(): LiveData<List<RepositoryView>> = mRepositories

    init {
        fetchRepositories("a", RepositorySortType.STARS)
    }

    override fun onSortByStarsSelected() {
        fetchRepositories("a", RepositorySortType.STARS)
    }

    override fun onSortByForksSelected() {
        fetchRepositories("a", RepositorySortType.FORKS)
    }

    override fun onSortByUpdatedSelected() {
        fetchRepositories("a", RepositorySortType.UPDATED)
    }

    override fun onFilterIconClick() {
        mShouldShowFilterDialog.value = Unit
    }

    override fun onSearchIconClick() {
        // TODO: Handle search
    }

    override fun onSearchRepositoriesTextChange(text: String) {
        mSearchTerm = text
    }

    private fun fetchRepositories(
        searchTerm: String,
        repositorySortType: RepositorySortType
    ) {
        viewModelScope.launch {
            mIsLoading.value = true
            getRepositoriesUseCase(Params(searchTerm, repositorySortType)).fold(
                ::handleFailure,
                ::handleRepositories
            )
            mIsLoading.value = false
        }
    }

    private fun handleFailure(failure: Failure) {
        // todo: Handle failure
    }

    private fun handleRepositories(repositories: Repositories) {
        _mRepositories.apply {
            clear()
            addAll(repositories.list)
        }
        mRepositories.value = repositories.list.toView()
    }
}
