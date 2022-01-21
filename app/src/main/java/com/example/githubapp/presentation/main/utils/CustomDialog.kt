package com.example.githubapp.presentation.main.utils

import android.app.AlertDialog
import android.content.Context
import androidx.viewbinding.ViewBinding

class CustomDialog<T>(
    context: Context,
    binding: ViewBinding
) : AlertDialog(context) {

    private var _binding: ViewBinding? = null
    private var mDialog: Builder = Builder(context)
    val dialogBinding get(): T = _binding!! as T

    init {
        _binding = binding
        setContentView(binding.root)
    }

    fun showDialog() {
        mDialog.show()
    }
}
