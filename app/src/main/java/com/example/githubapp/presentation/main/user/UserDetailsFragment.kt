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
import com.example.githubapp.presentation.main.utils.setClickableLink

class UserDetailsFragment : BaseFragment<FragmentUserDetailsBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentUserDetailsBinding
        get() = FragmentUserDetailsBinding::inflate

    private val args by navArgs<UserDetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            loadImage(args.user.avatarUrl, imageViewUserImage)
            textViewUserName.text = getString(R.string.userScreen_name, args.user.username)
            textViewId.text = getString(R.string.userScreen_id, args.user.id)
            textViewUrl.setClickableLink(
                getString(
                    R.string.userDetails_githubUrl,
                    removePrefix(args.user.url)
                )
            )
        }
    }

    private fun removePrefix(githubUrl: String): String {
        return githubUrl.drop(6)
    }
}
