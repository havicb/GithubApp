package com.example.githubapp

import android.app.Application
import androidx.viewbinding.BuildConfig
import com.example.githubapp.data.di.dataModule
import com.example.githubapp.di.networkModule
import com.example.githubapp.di.useCaseModule
import com.example.githubapp.presentation.base.baseModule
import com.example.githubapp.presentation.main.auth.authModule
import com.example.githubapp.presentation.main.error.errorModule
import com.example.githubapp.presentation.main.home.homeModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    networkModule,
                    baseModule,
                    homeModule,
                    dataModule,
                    useCaseModule,
                    authModule,
                    errorModule
                )
            )
        }

        initLogger()
    }

    private fun initLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.asTree())
        }
    }
}
