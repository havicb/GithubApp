package com.example.githubapp.data

import com.example.githubapp.NetworkHandler
import com.example.githubapp.core.Either
import com.example.githubapp.core.Failure
import kotlinx.coroutines.Deferred
import retrofit2.Response
import java.net.HttpURLConnection
import java.net.SocketTimeoutException

abstract class BaseRepository(private val networkHandler: NetworkHandler) {

    internal suspend inline fun <reified T> Deferred<Response<T>>.getResults(): Either<Failure, T> {
        if (!networkHandler.isNetworkAvailable()) {
            return Either.Left(Failure.NetworkConnectionFailure("No internet available"))
        }
        return try {
            val response = await()
            when (response.isSuccessful) {
                true -> Either.Right(response.body() ?: T::class.java.newInstance())
                false -> handleFailedResponse(response.code(), response.message())
            }
        } catch (ex: Exception) {
            when (ex) {
                is SocketTimeoutException -> Either.Left(Failure.NetworkConnectionFailure("Connection timed out"))
                else -> Either.Left(Failure.OtherFailure("Unknown error.. Try again in few minutes!"))
            }
        }
    }

    private fun handleFailedResponse(
        code: Int,
        message: String
    ): Either.Left<Failure> {
        return when (code) {
            HttpURLConnection.HTTP_INTERNAL_ERROR -> Either.Left(Failure.ServerFailure(message))
            HttpURLConnection.HTTP_BAD_REQUEST -> Either.Left(Failure.BadRequest(message))
            HttpURLConnection.HTTP_NOT_FOUND -> Either.Left(Failure.NotFound(message))
            HttpURLConnection.HTTP_FORBIDDEN -> Either.Left(Failure.Forbidden(message))
            HttpURLConnection.HTTP_UNAUTHORIZED -> Either.Left(Failure.NotAuthorized(message))
            else -> Either.Left(Failure.OtherFailure("Some error occurred!"))
        }
    }
}
