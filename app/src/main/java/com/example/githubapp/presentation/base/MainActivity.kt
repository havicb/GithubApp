package com.example.githubapp.presentation.base

import android.app.Activity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.githubapp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * Hides the keyboard form the screen.
     */
    fun hideKeyboard() {
        val manager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(currentFocus?.rootView?.windowToken, 0)
    }
}
