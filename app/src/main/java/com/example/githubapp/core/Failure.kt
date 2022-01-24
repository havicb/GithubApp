package com.example.githubapp.core

/**
 * Base class for handling errors.
 */
sealed class Failure {
    abstract class NetworkFailure(val message: String) : Failure()
    class ServerFailure(message: String) : NetworkFailure(message)
    class NotFound(message: String) : NetworkFailure(message)
    class NotAuthorized(message: String) : NetworkFailure(message)
    class Forbidden(message: String) : NetworkFailure(message)
    class BadRequest(message: String) : NetworkFailure(message)
    class NetworkConnectionFailure(message: String) : NetworkFailure(message)

    data class OtherFailure(val message: String) : Failure()
}
