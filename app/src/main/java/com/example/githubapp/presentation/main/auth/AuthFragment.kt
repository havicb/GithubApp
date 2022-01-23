package com.example.githubapp.presentation.main.auth

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.githubapp.BuildConfig
import com.example.githubapp.databinding.FragmentAuthBinding
import com.example.githubapp.presentation.base.BaseFragment
import org.koin.java.KoinJavaComponent

class AuthFragment : BaseFragment<FragmentAuthBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAuthBinding
        get() = FragmentAuthBinding::inflate

    private val mLogic: AuthLogic by KoinJavaComponent.inject(AuthLogic::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() = with(mLogic) {
        super.onStart()

        observeCallIntent().observe(viewLifecycleOwner) {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(
                    "https://github.com/login/oauth/authorize".plus("?client_id=${BuildConfig.CLIENT_ID}")
                        .plus("&scope=repo&redirect_uri=${BuildConfig.REDIRECT_CALLBACK}")
                )
            )
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        val uri = requireActivity().intent.data
        if (uri != null) {
            mLogic.onCodeAccquired(uri.getQueryParameter("code"))
        }
    }
}
