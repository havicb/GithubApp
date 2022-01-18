package com.example.githubapp.presentation.main.home

import androidx.lifecycle.LiveData
import com.example.githubapp.domain.entity.RepositoryEntity

interface HomeLogic {
    fun observeClick(): LiveData<List<RepositoryEntity>>
    fun onTextViewClick()
}