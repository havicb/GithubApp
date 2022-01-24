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
import com.example.githubapp.presentation.base.view.NavigationEvent
import com.example.githubapp.presentation.base.view.SingleLiveEvent
import com.example.githubapp.presentation.main.OwnerView
import com.example.githubapp.presentation.main.RepositoryView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getRepositoriesUseCase: GetRepositoriesUseCase
) : BaseViewModel(), HomeLogic {

    private val repositories = MutableLiveData<List<RepositoryView>>()
    private val shouldShowFilterDialog = SingleLiveEvent<Unit>()
    private val isLoading = MutableLiveData<Boolean>()
    private val genericError = SingleLiveEvent<Unit>()
    private val navEvent = SingleLiveEvent<NavigationEvent>()

    private val allRepositories = arrayListOf<RepositoryEntity>()
    private var defaultSearchTerm = "a"
    private var searchTerm = defaultSearchTerm
    private var currentPage = 1
    private var currentSortType: RepositorySortType? = null

    override val observeNavigation: LiveData<NavigationEvent>
        get() = navEvent
    override val observeGenericError: LiveData<Unit>
        get() = genericError
    override val observeLoading: LiveData<Boolean>
        get() = isLoading
    override val observeShouldShowFilterDialog: LiveData<Unit>
        get() = shouldShowFilterDialog
    override val observeRepositories: LiveData<List<RepositoryView>>
        get() = repositories

    init {
        fetchData()
    }

    override fun onRepositoryClick(repositoryView: RepositoryView) {
        navEvent.value = NavigationEvent.To(
            HomeFragmentDirections.actionHomeFragmentToRepositoryDetailsFragment(repositoryView)
        )
    }

    override fun onAvatarClick(ownerView: OwnerView) {
        navEvent.value = NavigationEvent.To(
            HomeFragmentDirections.actionHomeFragmentToUserDetailsFragment(ownerView)
        )
    }

    override fun fetchData() {
        fetchRepositories(searchTerm, currentSortType, currentPage)
    }

    override fun loadMore() {
        currentPage += 1
        viewModelScope.launch {
            getRepositoriesUseCase(Params(searchTerm, currentSortType, currentPage)).fold(
                ::handleFailure,
                ::handleMoreRepositories
            )
        }
    }

    override fun onSortByStarsSelected() {
        currentPage = 1
        fetchRepositories(searchTerm, RepositorySortType.STARS, currentPage)
    }

    override fun onSortByForksSelected() {
        currentPage = 1
        fetchRepositories(searchTerm, RepositorySortType.FORKS, currentPage)
    }

    override fun onSortByUpdatedSelected() {
        currentPage = 1
        fetchRepositories(searchTerm, RepositorySortType.UPDATED, currentPage)
    }

    override fun onFilterIconClick() {
        shouldShowFilterDialog.value = Unit
    }

    override fun onSearchIconClick() {
        fetchRepositories(searchTerm, null, 1)
    }

    override fun onSearchRepositoriesTextChange(text: String) {
        val formattedText = if (text.trim().isEmpty()) {
            defaultSearchTerm
        } else text.trim()
        searchTerm = formattedText
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
            isLoading.value = true
            delay(1000)
            getRepositoriesUseCase(Params(searchTerm, repositorySortType, page)).fold(
                ::handleFailure,
                ::handleRepositories
            )
            isLoading.value = false
        }
    }

    /**
     * Handling more repositories.
     * For purpose of this task this method is pretty much simple.
     */
    private fun handleMoreRepositories(repositories: Repositories) {
        allRepositories.addAll(repositories.list)
        this.repositories.value = allRepositories.toList().toView()
    }

    /**
     * In real life scenario this method would be much more complex.
     */
    private fun handleFailure(failure: Failure) {
        when (failure) {
            is Failure.NetworkFailure -> {
                navEvent.value =
                    NavigationEvent.To(HomeFragmentDirections.actionHomeFragmentToErrorFragment())
            }
            is Failure.OtherFailure -> genericError.value = Unit
        }
    }

    /**
     * Method for handling successful response.
     */
    private fun handleRepositories(repositories: Repositories) {
        allRepositories.apply {
            clear()
            addAll(repositories.list)
        }
        this.repositories.value = repositories.list.toView()
    }
}
