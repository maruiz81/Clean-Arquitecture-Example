package com.maruiz.pet.domain.usecases

import arrow.Kind2
import com.maruiz.pet.data.error.Failure
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

abstract class UseCase<DataType, in Params, out Type>() where Type : Any? {

    abstract suspend fun run(params: Params): Kind2<DataType, Failure, Type>

    operator fun invoke(
        params: Params,
        clientScope: CoroutineScope,
        onResult: (Kind2<DataType, Failure, Type>) -> Unit = {}
    ) {
        clientScope.launch {
            val job = async(Dispatchers.IO) { run(params) }
            onResult(job.await())
        }
    }
}