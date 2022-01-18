package com.example.githubapp.presentation.main.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.githubapp.core.Failure
import com.example.githubapp.domain.entity.Repositories
import com.example.githubapp.domain.entity.RepositoryEntity
import com.example.githubapp.domain.repositories.GetRepositoriesUseCase
import com.example.githubapp.domain.repositories.Params
import com.example.githubapp.presentation.base.BaseViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeViewModel(
    private val getRepositoriesUseCase: GetRepositoriesUseCase
) : BaseViewModel(), HomeLogic {

    private val mClick = MutableLiveData<List<RepositoryEntity>>()

    override fun observeClick(): LiveData<List<RepositoryEntity>> = mClick

    init {
        viewModelScope.launch {
            getRepositoriesUseCase(Params("a")).fold(::handleFailure, ::handleRepositories)
        }
    }

    private fun handleFailure(failure: Failure) {
    }

    private fun handleRepositories(repositories: Repositories) {
        mClick.value = repositories.list
    }

    override fun onTextViewClick() {
        Timber.d("CALLED")
    }
}
