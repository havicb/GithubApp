package com.example.githubapp.presentation.main.auth

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.githubapp.R
import com.example.githubapp.databinding.FragmentAuthBinding
import com.example.githubapp.presentation.base.BaseFragment
import com.example.githubapp.presentation.main.utils.gitHubOAuth
import com.example.githubapp.presentation.main.utils.hideView
import com.example.githubapp.presentation.main.utils.showView
import org.koin.java.KoinJavaComponent

class AuthFragment : BaseFragment<FragmentAuthBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAuthBinding
        get() = FragmentAuthBinding::inflate

    private val logic: AuthLogic by KoinJavaComponent.inject(AuthLogic::class.java)

    override fun onStart() = with(logic) {
        super.onStart()

        observeIsLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                onLoading()
            } else {
                onStopLoading()
            }
        }

        observeNavigation.observe(viewLifecycleOwner) {
            handleNavigation(it)
        }

        observeShouldCallIntent.observe(viewLifecycleOwner) {
            startActivity(Intent().gitHubOAuth())
        }
    }

    private fun onLoading() = with(binding) {
        groupAuthorizationButtons.hideView()
        progressBarLoadingProgress.showView()
    }

    private fun onStopLoading() = with(binding) {
        groupAuthorizationButtons.showView()
        progressBarLoadingProgress.hideView()
    }

    override fun onResume() {
        super.onResume()
        val uri = requireActivity().intent.data
        if (uri != null) {
            logic.onCodeAccquired(uri.getQueryParameter(getString(R.string.homeScreen_queryCodeParameter)))
        }
    }

    override fun setListeners() = with(binding) {
        super.setListeners()
        buttonSkip.setOnClickListener {
            logic.onSkipButton()
        }

        buttonAuthorization.setOnClickListener {
            logic.onAuthButton()
        }
    }
}
