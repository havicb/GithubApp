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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getRepositoriesUseCase: GetRepositoriesUseCase
) : BaseViewModel(), HomeLogic {

    private val mRepositories = MutableLiveData<List<RepositoryView>>()
    private val mShouldShowFilterDialog = SingleLiveEvent<Unit>()
    private val mIsLoading = MutableLiveData<Boolean>()
    private val mRepositoryFailedToLoad = SingleLiveEvent<Unit>()
    private val mGenericError = SingleLiveEvent<Unit>()

    private val _mRepositories = arrayListOf<RepositoryEntity>()
    private var mSearchTerm = "a"
    private var mCurrentPage = 1
    private var mCurrentSortType: RepositorySortType? = null

    override fun observeGenericError(): LiveData<Unit> = mGenericError
    override fun observeRepositoriesLoadingFailure(): LiveData<Unit> = mRepositoryFailedToLoad
    override fun observeIsLoading(): LiveData<Boolean> = mIsLoading
    override fun observeShouldShowFilterDialog(): LiveData<Unit> = mShouldShowFilterDialog
    override fun observeRepositories(): LiveData<List<RepositoryView>> = mRepositories

    override fun fetchData() {
        fetchRepositories(mSearchTerm, mCurrentSortType, mCurrentPage)
    }

    override fun loadMore() {
        mCurrentPage += 1
        viewModelScope.launch {
            getRepositoriesUseCase(Params(mSearchTerm, mCurrentSortType, mCurrentPage)).fold(
                ::handleFailure,
                ::handleMoreRepositories
            )
        }
    }

    override fun onSortByStarsSelected() {
        mCurrentPage = 1
        fetchRepositories(mSearchTerm, RepositorySortType.STARS, mCurrentPage)
    }

    override fun onSortByForksSelected() {
        mCurrentPage = 1
        fetchRepositories(mSearchTerm, RepositorySortType.FORKS, mCurrentPage)
    }

    override fun onSortByUpdatedSelected() {
        mCurrentPage = 1
        fetchRepositories(mSearchTerm, RepositorySortType.UPDATED, mCurrentPage)
    }

    override fun onFilterIconClick() {
        mShouldShowFilterDialog.value = Unit
    }

    override fun onSearchIconClick() {
        fetchRepositories(mSearchTerm, null, 1)
    }

    override fun onSearchRepositoriesTextChange(text: String) {
        val formattedText = if (text.trim().isEmpty()) {
            "a"
        } else text.trim()
        mSearchTerm = formattedText
    }

    /**
     * In order to avoid repeating same code, logic for fetching repository is extracted here.
     */
    private fun fetchRepositories(
        searchTerm: String,
        repositorySortType: RepositorySortType?,
        page: Int
    ) {
        viewModelScope.launch {
            mIsLoading.value = true
            delay(1000)
            getRepositoriesUseCase(Params(searchTerm, repositorySortType, page)).fold(
                ::handleFailure,
                ::handleRepositories
            )
            mIsLoading.value = false
        }
    }

    /**
     * Handling more repositories.
     * For purpose of this task this method is pretty much simple.
     */
    private fun handleMoreRepositories(repositories: Repositories) {
        _mRepositories.addAll(repositories.list)
        mRepositories.value = _mRepositories.toList().toView()
    }

    /**
     * In real life scenario this method would be much more complex.
     */
    private fun handleFailure(failure: Failure) {
        when (failure) {
            is Failure.NetworkFailure -> mRepositoryFailedToLoad.value = Unit
            is Failure.OtherFailure -> mGenericError.value = Unit
        }
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
