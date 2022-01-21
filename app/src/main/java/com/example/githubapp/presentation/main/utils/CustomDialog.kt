package com.example.githubapp.presentation.main.utils

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding

class CustomDialog<T : ViewBinding>(
    context: Context,
    layoutInflator: (LayoutInflater) -> T
) : AlertDialog(context) {

    private var _binding: ViewBinding? = null
    var mDialog: Builder

    init {
        mDialog = Builder(context)
        _binding = layoutInflator.invoke(LayoutInflater.from(context))
        setContentView(binding.root)
    }

    @Suppress("UNCHECKED_CAST")
    internal val binding
        get() = _binding as T

    fun showDialog() {
        mDialog.show()
    }
}
