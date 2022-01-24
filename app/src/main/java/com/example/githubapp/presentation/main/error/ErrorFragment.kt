package com.example.githubapp.presentation.main.error

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.githubapp.core.extensions.navController
import com.example.githubapp.databinding.FragmentErrorBinding
import com.example.githubapp.presentation.base.BaseFragment
import com.example.githubapp.presentation.main.home.HomeLogic
import org.koin.java.KoinJavaComponent.inject

class ErrorFragment : BaseFragment<FragmentErrorBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentErrorBinding
        get() = FragmentErrorBinding::inflate

    private val logic: ErrorLogic by inject(HomeLogic::class.java)

    companion object {
        const val ERROR_FRAGMENT_KEY = "ERROR_FRAGMENT_KEY"
    }

    override fun setListeners() = with(binding) {
        retryButton.setOnClickListener {
            navController.previousBackStackEntry?.savedStateHandle?.set(ERROR_FRAGMENT_KEY, true)
            logic.onRetryButton()
        }
    }

    override fun onStart() {
        super.onStart()

        logic.observeNavigation.observe(viewLifecycleOwner) {
            handleNavigation(it)
        }
    }
}
