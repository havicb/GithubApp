package com.example.githubapp.domain.base

import com.example.githubapp.core.Either
import com.example.githubapp.core.Failure

/**
 * Abstract class for Use-Case/Interactor.
 * Any interactor/use-case in application should implement this class.
 */
abstract class BaseUseCase<out T, in Params> where T : Any {
    abstract suspend fun run(params: Params): Either<Failure, T>
    suspend operator fun invoke(params: Params): Either<Failure, T> = run(params)
}
