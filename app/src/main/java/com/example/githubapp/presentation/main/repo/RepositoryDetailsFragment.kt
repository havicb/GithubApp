package com.example.githubapp.presentation.main.repo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.githubapp.R
import com.example.githubapp.databinding.FragmentRepositoryDetailsBinding
import com.example.githubapp.presentation.base.BaseFragment
import java.text.SimpleDateFormat
import java.util.*

class RepositoryDetailsFragment : BaseFragment<FragmentRepositoryDetailsBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRepositoryDetailsBinding
        get() = FragmentRepositoryDetailsBinding::inflate

    private val mArgs by navArgs<RepositoryDetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setScreen()
    }

    private fun setScreen() = with(mArgs.repository) {
        binding.textViewFullName.text = fullName
        binding.textViewCreatedAt.text =
            getString(R.string.repositoryDetails_createdAt, createdAt)
        binding.textViewLastUpdated.text =
            getString(R.string.repositoryDetails_updatedAt, modifiedAt)
        binding.textViewProgrammingLanguage.text =
            getString(R.string.repositoryDetails_programmingLanguage, language)
        binding.textViewGithubUrl.text = gitHubUrl
        Glide.with(requireContext())
            .load(ownerView.avatarUrl)
            .circleCrop()
            .into(binding.imageViewAutorImage)
    }
}
