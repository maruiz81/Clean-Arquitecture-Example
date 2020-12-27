package com.maruiz.pet.data.repository

import arrow.core.Either
import com.maruiz.pet.data.error.Failure
import com.maruiz.pet.data.extension.makeRequest
import com.maruiz.pet.data.model.BookDataModel
import com.maruiz.pet.data.service.BookService

interface BookRepository {
    suspend fun getBooks(): Either<Failure, List<BookDataModel>>

    class Network(private val bookService: BookService) : BookRepository {
        override suspend fun getBooks(): Either<Failure, List<BookDataModel>> =
            bookService.getBooks().makeRequest({ it }, emptyList())
    }
}