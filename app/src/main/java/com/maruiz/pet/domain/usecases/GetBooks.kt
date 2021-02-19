package com.maruiz.pet.domain.usecases

import arrow.core.Either
import arrow.core.ForEither
import com.maruiz.pet.data.error.Failure
import com.maruiz.pet.data.repository.BookRepository
import com.maruiz.pet.domain.mapper.convertToDomain
import com.maruiz.pet.domain.model.BookDomainModel

class GetBooks(private val bookRepository: BookRepository) :
    UseCase<ForEither, Unit, List<BookDomainModel>>() {
    override suspend fun run(params: Unit): Either<Failure, List<BookDomainModel>> =
        bookRepository.getBooks().map { success -> success.map { it.convertToDomain() }}
}