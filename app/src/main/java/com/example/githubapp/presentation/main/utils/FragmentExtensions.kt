package com.example.githubapp.core.extensions

import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide

fun Fragment.toast(text: String) {
    Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
}

/**
 * Add navController variable to all fragments.
 */
val Fragment.navController: NavController
    get() = NavHostFragment.findNavController(this)

/**
 * Extension for fast image loading with glide.
 */
fun Fragment.loadImage(source: String, imageView: ImageView) {
    Glide.with(requireContext())
        .load(source)
        .into(imageView)
}

/**
 * Add circle crop to image.
 */
fun Fragment.loadCircleImage(source: String, imageView: ImageView) {
    Glide.with(requireContext())
        .load(source)
        .circleCrop()
        .into(imageView)
}
