package com.example.githubapp.presentation.main.home

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.githubapp.core.extensions.navController
import com.example.githubapp.databinding.FragmentHomeBinding
import com.example.githubapp.databinding.SortDialogBinding
import com.example.githubapp.domain.entity.UserView
import com.example.githubapp.presentation.base.BaseFragment
import com.example.githubapp.presentation.main.error.ErrorFragment
import com.example.githubapp.presentation.main.utils.MarginatedVerticalItemDecorator
import com.example.githubapp.presentation.main.utils.onActionSearch
import com.example.githubapp.presentation.main.utils.showView
import com.example.githubapp.presentation.main.utils.showViewByIndex
import org.koin.java.KoinJavaComponent.inject

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    private lateinit var dialog: Dialog

    private val logic: HomeLogic by inject(HomeLogic::class.java)
    private val repositoriesAdapter: HomeRepositoriesAdapter by inject(HomeRepositoriesAdapter::class.java)

    private val args: HomeFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        args.loggedUser?.let {
            onLoggedUser(it)
        }
        setScreen()
    }

    override fun onStart() = with(logic) {
        super.onStart()

        // In real case scenario I would create generic extension for this.
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<Boolean>(ErrorFragment.ERROR_FRAGMENT_KEY)
            ?.observe(viewLifecycleOwner) { shouldFetch ->
                if (shouldFetch) {
                    logic.fetchData()
                }
            }

        observeNavigation.observe(viewLifecycleOwner) {
            handleNavigation(it)
        }

        observeGenericError.observe(viewLifecycleOwner) {
            showSnackbar()
        }

        observeLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                showShimmer()
            } else {
                showRecyclerView()
            }
        }

        observeRepositories.observe(viewLifecycleOwner) {
            repositoriesAdapter.list = it
        }

        observeShouldShowFilterDialog.observe(viewLifecycleOwner) {
            showFilterDialog()
        }
    }

    override fun setListeners() = with(binding) {
        imageViewFilterIcon.setOnClickListener {
            logic.onFilterIconClick()
        }

        imageViewSearchIcon.setOnClickListener {
            logic.onSearchIconClick()
        }
    }

    private fun setScreen() {
        dialog = Dialog(requireContext())
        initRecyclerView()
        setUpEditText()
    }

    private fun setUpEditText() = with(binding) {
        editTextSearchRepositories.apply {
            doAfterTextChanged {
                logic.onSearchRepositoriesTextChange(it.toString())
            }

            onActionSearch {
                hideKeyboard()
                logic.onSearchIconClick()
                false
            }
        }
    }

    /**
     * Private method that handles initialization and decoration of recycler view
     * Also initialize all listeners that are coupled to RV
     */
    private fun initRecyclerView() = with(binding.recyclerViewRepositories) {
        adapter = repositoriesAdapter
        addItemDecoration(
            MarginatedVerticalItemDecorator(
                requireContext(),
                12
            )
        )
        repositoriesAdapter.setOnRepositoryItemClickListener {
            logic.onRepositoryClick(it)
        }

        repositoriesAdapter.setOnAvatarClickListener {
            logic.onAvatarClick(it)
        }

        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    logic.loadMore()
                }
            }
        })
    }

    private fun onLoggedUser(user: UserView) = with(binding) {
        cardUserCredentials.showView()
        textViewUsername.text = user.username
        textViewLocation.text = user.location
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
        dialog.setContentView(dialogBinding.root)
        with(dialogBinding) {
            textViewSortByForks.setOnClickListener {
                onDialogItemClick()
                logic.onSortByForksSelected()
            }
            textViewSortByStars.setOnClickListener {
                onDialogItemClick()
                logic.onSortByStarsSelected()
            }
            textViewSortByUpdated.setOnClickListener {
                onDialogItemClick()
                logic.onSortByUpdatedSelected()
            }
        }
        dialog.show()
    }

    /**
     * In order to avoid code duplication, common behaviour is extracted here.
     */
    private fun onDialogItemClick() {
        binding.recyclerViewRepositories.scrollToPosition(0)
        dialog.dismiss()
    }
}
