package com.maruiz.pet.domain.usecases

import arrow.core.Left
import arrow.core.Right
import arrow.core.fix
import com.maruiz.pet.data.error.Failure
import com.maruiz.pet.data.model.BookDataModel
import com.maruiz.pet.data.repository.BookRepository
import com.maruiz.pet.domain.di.domainModule
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class GetBooksTest : AutoCloseKoinTest() {

    @Mock
    private lateinit var bookRepository: BookRepository

    private val getBooks: GetBooks by inject()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        startKoin {
            modules(
                listOf(
                    domainModule,
                    module(override = true) {
                        factory { bookRepository }
                    }
                )
            )
        }
    }

    @Test
    fun `should return the list of books when success`() {
        runBlocking {
            `when`(bookRepository.getBooks()).thenReturn(Right(getBookList()))

            getBooks.run(Unit).fix().fold({ assert(false) }) {
                it.size shouldBeEqualTo 10
                it.forEachIndexed { index, item ->
                    (index + 1).let {
                        "Title $it" shouldBeEqualTo item.title
                        "Author $it" shouldBeEqualTo item.author
                        "Short synopsis $it" shouldBeEqualTo item.shortSynopsis
                        "Synopsis $it" shouldBeEqualTo item.synopsis
                        "Image $it" shouldBeEqualTo item.image
                    }
                }
            }
        }
    }

    private fun getBookList() =
        (1..10).map { index ->
            BookDataModel(
                bookId = index,
                title = "Title $index",
                author = "Author $index",
                shortSynopsis = "Short synopsis $index",
                synopsis = "Synopsis $index",
                image = "Image $index",
                published = 2018,
                genres = listOf("classic", "fiction")
            )
        }

    @Test
    fun `should return failure when there is an error in the call`() {
        runBlocking {
            `when`(bookRepository.getBooks()).thenReturn(Left(Failure.GenericError))

            getBooks.run(Unit).fix()
                .fold({ it shouldBeInstanceOf Failure.GenericError::class }) { assert(false) }
        }
    }
}