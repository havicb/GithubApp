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
    private var mSearchTerm = "a"
    private var mCurrentSortType: RepositorySortType? = null

    override fun observeIsLoading(): LiveData<Boolean> = mIsLoading
    override fun observeShouldShowFilterDialog(): LiveData<Unit> = mShouldShowFilterDialog
    override fun observeRepositories(): LiveData<List<RepositoryView>> = mRepositories

    init {
        fetchRepositories(mSearchTerm, mCurrentSortType)
    }

    override fun onSortByStarsSelected() {
        fetchRepositories(mSearchTerm, RepositorySortType.STARS)
    }

    override fun onSortByForksSelected() {
        fetchRepositories(mSearchTerm, RepositorySortType.FORKS)
    }

    override fun onSortByUpdatedSelected() {
        fetchRepositories(mSearchTerm, RepositorySortType.UPDATED)
    }

    override fun onFilterIconClick() {
        mShouldShowFilterDialog.value = Unit
    }

    override fun onSearchIconClick() {
        fetchRepositories(mSearchTerm, null)
    }

    override fun onSearchRepositoriesTextChange(text: String) {
        mSearchTerm = text.trim()
    }

    /**
     * In order to avoid repeating same code, logic for fetching repository is extracted here.
     */
    private fun fetchRepositories(
        searchTerm: String,
        repositorySortType: RepositorySortType?
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

    /**
     * In real life scenario this method would be much more complex.
     */
    private fun handleFailure(failure: Failure) {
        navigate(HomeFragmentDirections.actionHomeFragmentToErrorFragment())
    }

    /**
     * Method for handling successful response.
     */
    private fun handleRepositories(repositories: Repositories) {
        _mRepositories.apply {
            clear()
            addAll(repositories.list)
        }
        mRepositories.value = repositories.list.toView()
    }
}
