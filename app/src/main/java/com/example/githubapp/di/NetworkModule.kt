package com.example.githubapp.di

import com.example.githubapp.BuildConfig
import com.example.githubapp.NetworkHandler
import com.example.githubapp.NetworkHandlerImpl
import com.example.githubapp.data.DefaultNetworkConfig
import com.example.githubapp.data.NetworkConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { DefaultNetworkConfig() } bind NetworkConfig::class
    single<Interceptor> {
        HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
    }
    single { NetworkHandlerImpl(get()) } bind NetworkHandler::class
    single<Gson> {
        GsonBuilder()
            .setLenient()
            .create()
    }
    single { createOkHttpClient(get(), get()) }
    single(named("githubRetrofit")) { createGithubRetrofit(okHttpClient = get(), gson = get()) }
    single(named("authRetrofit")) { createAuthRetrofit(get(), get()) }
}

private fun createOkHttpClient(
    networkConfig: NetworkConfig,
    httpLoggingInterceptor: Interceptor
): OkHttpClient {
    val builder = OkHttpClient.Builder().dispatcher(Dispatcher())
    builder.addInterceptor(httpLoggingInterceptor)
    return builder.connectTimeout(networkConfig.connectTimeoutMs, TimeUnit.MILLISECONDS)
        .readTimeout(networkConfig.readTimeoutMs, TimeUnit.MILLISECONDS)
        .writeTimeout(networkConfig.writeTimeoutMs, TimeUnit.MILLISECONDS)
        .build()
}

private fun createGithubRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
    return createRetrofit(ApiUrl.API, gson, okHttpClient)
}

private fun createAuthRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
    return createRetrofit(ApiUrl.GITHUB_AUTH, gson, okHttpClient)
}

private fun createRetrofit(baseUrl: ApiUrl, gson: Gson, okHttpClient: OkHttpClient): Retrofit {
    val apiBaseUrl =
        if (baseUrl == ApiUrl.API) BuildConfig.API_BASE_URL else BuildConfig.GITHUB_AUTH_URL
    return Retrofit.Builder()
        .baseUrl(apiBaseUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()
}

enum class ApiUrl {
    API, GITHUB_AUTH
}
