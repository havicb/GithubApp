package com.example.githubapp.presentation.main.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.githubapp.R
import com.example.githubapp.core.extensions.loadImage
import com.example.githubapp.databinding.FragmentUserDetailsBinding
import com.example.githubapp.presentation.base.BaseFragment

class UserDetailsFragment : BaseFragment<FragmentUserDetailsBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentUserDetailsBinding
        get() = FragmentUserDetailsBinding::inflate

    private val args by navArgs<UserDetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            loadImage(args.owner.avatarUrl, imageViewUserImage)
            textViewUserName.text = getString(R.string.userScreen_name)
            textViewLocation.text = getString(R.string.userScreen_location)
        }
    }
}
