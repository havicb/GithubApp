package com.example.githubapp.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.githubapp.core.extensions.navController
import com.example.githubapp.presentation.base.view.NavigationEvent
import org.koin.java.KoinJavaComponent.inject

abstract class BaseFragment<T : ViewBinding> : Fragment() {

    private var _binding: ViewBinding? = null
    private val viewLogic: BaseLogic by inject(BaseLogic::class.java)

    @Suppress("UNCHECKED_CAST")
    internal val binding: T
        get() = _binding as T

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    override fun onStart() {
        super.onStart()
        viewLogic.observeNavigationEvent().observe(viewLifecycleOwner) {
            when (it) {
                NavigationEvent.Back -> navController.popBackStack()
                is NavigationEvent.To -> navController.navigate(it.directions)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected fun hideKeyboard() {
        (requireActivity() as MainActivity).hideKeyboard()
    }

    /**
     * Initialize all available listeners on particular screen.
     */
    open fun setListeners() {}
}
