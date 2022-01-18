package com.example.githubapp.data

import com.example.githubapp.BuildConfig

interface NetworkConfig {
    val baseUrl: String
    val connectTimeoutMs: Long
    val readTimeoutMs: Long
    val writeTimeoutMs: Long
}

class DefaultNetworkConfig : NetworkConfig {
    override val baseUrl: String
        get() = BuildConfig.API_BASE_URL
    override val connectTimeoutMs: Long
        get() = DEFAULT_TIMEOUT_MS
    override val readTimeoutMs: Long
        get() = DEFAULT_TIMEOUT_MS
    override val writeTimeoutMs: Long
        get() = DEFAULT_TIMEOUT_MS

    companion object {
        private const val DEFAULT_TIMEOUT_MS: Long = 20_000
    }
}
