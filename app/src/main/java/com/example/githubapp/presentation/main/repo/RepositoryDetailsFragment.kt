package com.example.githubapp.presentation.main.repo

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.navArgs
import com.example.githubapp.R
import com.example.githubapp.core.extensions.loadCircleImage
import com.example.githubapp.databinding.FragmentRepositoryDetailsBinding
import com.example.githubapp.presentation.base.BaseFragment
import com.example.githubapp.presentation.main.utils.parseIsoToReadableDate

class RepositoryDetailsFragment : BaseFragment<FragmentRepositoryDetailsBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRepositoryDetailsBinding
        get() = FragmentRepositoryDetailsBinding::inflate

    private val args by navArgs<RepositoryDetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setScreen()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setScreen() = with(args.repository) {
        binding.textViewFullName.text = fullName
        binding.textViewCreatedAt.text =
            getString(
                R.string.repositoryDetails_createdAt,
                createdAt.parseIsoToReadableDate()
            )
        binding.textViewLastUpdated.text =
            getString(
                R.string.repositoryDetails_updatedAt,
                modifiedAt.parseIsoToReadableDate()
            )
        binding.textViewProgrammingLanguage.text =
            getString(R.string.repositoryDetails_programmingLanguage, language)
        binding.textViewGithubUrl.apply {
            movementMethod = LinkMovementMethod.getInstance()
            text = Html.fromHtml(
                getString(
                    R.string.repositoryDetails_githubUrl,
                    removeGitPrefix(gitHubUrl)
                )
            )
        }
        loadCircleImage(ownerView.avatarUrl, binding.imageViewAutorImage)
    }

    /**
     * Remove git:// from string.
     */
    private fun removeGitPrefix(githubUrl: String): String {
        return githubUrl.drop(6)
    }
}
