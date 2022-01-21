package com.example.githubapp.presentation.main.home

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import com.example.githubapp.R
import com.example.githubapp.core.extensions.navController
import com.example.githubapp.databinding.FragmentHomeBinding
import com.example.githubapp.databinding.SortDialogBinding
import com.example.githubapp.presentation.base.BaseFragment
import com.example.githubapp.presentation.main.utils.MarginatedVerticalItemDecorator
import com.example.githubapp.presentation.main.utils.showViewByIndex
import org.koin.java.KoinJavaComponent.inject

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    private val mLogic: HomeLogic by inject(HomeLogic::class.java)
    private val mAdapter: HomeRepositoriesAdapter by inject(HomeRepositoriesAdapter::class.java)
    private lateinit var mDialog: Dialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mDialog = Dialog(requireContext())

        initRecyclerView()
        setUpEditText()

        binding.imageViewFilterIcon.setOnClickListener {
            mLogic.onFilterIconClick()
        }

        binding.imageViewSearchIcon.setOnClickListener {
            mLogic.onSearchIconClick()
        }
    }

    override fun onStart() {
        super.onStart()

        mLogic.observeIsLoading().observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                showShimmer()
            } else {
                showRecyclerView()
            }
        }

        mLogic.observeRepositories().observe(viewLifecycleOwner) {
            mAdapter.mList = it
        }

        mLogic.observeShouldShowFilterDialog().observe(viewLifecycleOwner) {
            showFilterDialog()
        }
    }

    private fun setUpEditText() = with(binding) {
        editTextSearchRepositories.apply {
            doAfterTextChanged {
                mLogic.onSearchRepositoriesTextChange(it.toString())
            }
        }
    }

    /**
     * Private method that handles initialization and decoration of recycler view
     * Also initialize all listeners that are coupled to RV
     */
    private fun initRecyclerView() = with(binding.recyclerViewRepositories) {
        adapter = mAdapter
        addItemDecoration(
            MarginatedVerticalItemDecorator(
                requireContext(),
                12
            )
        )
        mAdapter.setOnRepositoryItemClickListener {
            navController.navigate(
                HomeFragmentDirections.actionHomeFragmentToRepositoryDetailsFragment(it)
            )
        }

        mAdapter.setOnAvatarClickListener {
            navController.navigate(
                HomeFragmentDirections.actionHomeFragmentToUserDetailsFragment()
            )
        }
    }

    private fun showShimmer() = with(binding) {
        viewFlipper.showViewByIndex(0)
    }

    private fun showRecyclerView() = with(binding) {
        viewFlipper.showViewByIndex(1)
    }

    private fun showFilterDialog() {
        // todo: Move this to separate class.
        val dialogBinding = SortDialogBinding.inflate(LayoutInflater.from(requireContext()))
        mDialog.setContentView(dialogBinding.root)
        mDialog.setTitle(requireContext().getString(R.string.homeScreen_dialogTitle))
        with(dialogBinding) {
            textViewSortByForks.setOnClickListener {
                mLogic.onSortByForksSelected()
                mDialog.dismiss()
            }
            textViewSortByStars.setOnClickListener {
                mLogic.onSortByStarsSelected()
                mDialog.dismiss()
            }
            textViewSortByUpdated.setOnClickListener {
                mLogic.onSortByUpdatedSelected()
                mDialog.dismiss()
            }
        }
        mDialog.show()
    }
}
