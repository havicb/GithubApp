package com.example.githubapp.presentation.main.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.githubapp.databinding.FragmentUserDetailsBinding
import com.example.githubapp.presentation.base.BaseFragment

class UserDetailsFragment : BaseFragment<FragmentUserDetailsBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentUserDetailsBinding
        get() = FragmentUserDetailsBinding::inflate

    private val mArgs by navArgs<UserDetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            Glide.with(requireContext())
                .load(mArgs.owner.avatarUrl)
                .into(imageViewUserImage)
        }
    }
}
