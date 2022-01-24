package com.example.githubapp.presentation.main.utils

import android.content.Intent
import android.net.Uri
import com.example.githubapp.BuildConfig

/**
 * Creates an intent for navigation to GitHub OAuth login page.
 */
fun Intent.gitHubOAuth(): Intent {
    action = Intent.ACTION_VIEW
    data = Uri.parse(
        "https://github.com/login/oauth/authorize".plus("?client_id=${BuildConfig.CLIENT_ID}")
            .plus("&scope=repo&redirect_uri=${BuildConfig.REDIRECT_CALLBACK}")
    )
    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    return this
}
