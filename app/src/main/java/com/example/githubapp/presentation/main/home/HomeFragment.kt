package com.example.githubapp.presentation.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.githubapp.core.extensions.toast
import com.example.githubapp.databinding.FragmentHomeBinding
import com.example.githubapp.presentation.base.BaseFragment
import com.example.githubapp.presentation.main.utils.MarginatedVerticalItemDecorator
import org.koin.java.KoinJavaComponent.inject

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    private val viewLogic: HomeLogic by inject(HomeLogic::class.java)
    private val mAdater: HomeRepositoriesAdapter by inject(HomeRepositoriesAdapter::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() = with(binding.recyclerViewRepositories) {
        adapter = mAdater
        addItemDecoration(
            MarginatedVerticalItemDecorator(
                requireContext(),
                12
            )
        )
        mAdater.setOnItemClickListener {
            toast("Clicked")
        }
    }

    override fun onStart() {
        super.onStart()
        viewLogic.observeClick().observe(viewLifecycleOwner) {
            binding.viewFlipper.showNext()
            mAdater.submitList(it)
        }
    }
}
