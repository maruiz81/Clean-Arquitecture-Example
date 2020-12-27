package com.maruiz.pet.domain.usecases

import arrow.core.Either
import arrow.core.ForEither
import com.maruiz.pet.data.error.Failure
import com.maruiz.pet.data.repository.BookRepository
import com.maruiz.pet.domain.model.BookModelDomainModel

class GetBooks(private val bookRepository: BookRepository) :
    UseCase<ForEither, Unit, List<BookModelDomainModel>>() {
    override suspend fun run(params: Unit): Either<Failure, List<BookModelDomainModel>> =
        bookRepository.getBooks().map { success ->
            success.map {
                BookModelDomainModel(
                    it.title,
                    it.author,
                    it.shortSynopsis,
                    it.synopsis,
                    it.image
                )
            }
        }
}