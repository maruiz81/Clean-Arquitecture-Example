package com.maruiz.pet.data.extension

import android.util.Log
import arrow.core.Either
import arrow.core.Left
import arrow.core.Right
import arrow.core.flatMap
import com.maruiz.pet.data.error.Failure
import retrofit2.Call
import retrofit2.HttpException

suspend fun <T, R> Call<T>.makeRequest(transform: (T) -> R, default: R): Either<Failure, R> =
    Either.catch { execute() }
        .flatMap {
            if (it.isSuccessful) {
                val body = it.body()
                Right(if (body != null) transform(body) else default)
            } else Left(HttpException(it))
        }.mapLeft {
            Log.e(this.javaClass.simpleName, "Error making a call to the server", it)
            getFailureType(it)
        }

private fun getFailureType(throwable: Throwable): Failure = Failure.GenericError