package com.example.githubapp.presentation.main.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.githubapp.R
import com.example.githubapp.core.extensions.navController
import com.example.githubapp.databinding.FragmentAuthBinding
import com.example.githubapp.presentation.base.BaseFragment
import com.example.githubapp.presentation.main.utils.gitHubOAuth
import com.example.githubapp.presentation.main.utils.hideView
import com.example.githubapp.presentation.main.utils.showView
import org.koin.java.KoinJavaComponent

class AuthFragment : BaseFragment<FragmentAuthBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAuthBinding
        get() = FragmentAuthBinding::inflate

    private val mLogic: AuthLogic by KoinJavaComponent.inject(AuthLogic::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() = with(mLogic) {
        super.onStart()

        observeLoginLoading().observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                onLoginLoading()
            } else {
                onStopLoginLoading()
            }
        }

        observeNavigation().observe(viewLifecycleOwner) {
            navController.navigate(AuthFragmentDirections.actionAuthFragmentToHomeFragment(it))
        }

        observeCallIntent().observe(viewLifecycleOwner) {
            startActivity(Intent().gitHubOAuth())
        }
    }

    private fun onLoginLoading() = with(binding) {
        groupAuthorizationButtons.hideView()
        progressBarLoadingProgress.showView()
    }

    private fun onStopLoginLoading() = with(binding) {
        groupAuthorizationButtons.showView()
        progressBarLoadingProgress.hideView()
    }

    override fun onResume() {
        super.onResume()
        val uri = requireActivity().intent.data
        if (uri != null) {
            mLogic.onCodeAccquired(uri.getQueryParameter(getString(R.string.homeScreen_queryCodeParameter)))
        }
    }

    override fun setListeners() = with(binding) {
        super.setListeners()
        buttonSkip.setOnClickListener {
            mLogic.onSkipButton()
        }

        buttonAuthorization.setOnClickListener {
            mLogic.onAuthButton()
        }
    }
}
