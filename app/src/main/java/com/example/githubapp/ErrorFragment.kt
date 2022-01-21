package com.example.githubapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.githubapp.core.extensions.navController
import com.example.githubapp.databinding.FragmentErrorBinding
import com.example.githubapp.presentation.base.BaseFragment

class ErrorFragment : BaseFragment<FragmentErrorBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentErrorBinding
        get() = FragmentErrorBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textViewErrorGenericMessage.setOnClickListener {
            navController.popBackStack()
        }
    }
}
