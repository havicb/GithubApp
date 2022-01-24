package com.example.githubapp

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.githubapp.core.extensions.navController
import com.example.githubapp.databinding.FragmentErrorBinding
import com.example.githubapp.presentation.base.BaseFragment

class ErrorFragment : BaseFragment<FragmentErrorBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentErrorBinding
        get() = FragmentErrorBinding::inflate

    companion object {
        const val ERROR_FRAGMENT_KEY = "ERROR_FRAGMENT_KEY"
    }

    override fun setListeners() = with(binding) {
        retryButton.setOnClickListener {
            navController.previousBackStackEntry?.savedStateHandle?.set(ERROR_FRAGMENT_KEY, true)
            navController.popBackStack()
        }
    }
}
