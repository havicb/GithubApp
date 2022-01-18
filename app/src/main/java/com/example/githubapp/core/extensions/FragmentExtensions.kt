package com.example.githubapp.core.extensions

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

fun Fragment.toast(text: String) {
    Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
}

val Fragment.navController: NavController
    get() = NavHostFragment.findNavController(this)
