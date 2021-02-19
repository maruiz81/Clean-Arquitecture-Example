package com.maruiz.pet.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import arrow.core.Either
import arrow.core.Left
import arrow.core.Right
import com.maruiz.pet.data.error.Failure
import com.maruiz.pet.domain.model.BookDomainModel
import com.maruiz.pet.domain.usecases.GetBooks
import com.maruiz.pet.presentation.di.PresentationNaming
import com.maruiz.pet.presentation.di.presentationModule
import com.nhaarman.mockitokotlin2.any
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class BookListViewModelTest : AutoCloseKoinTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var getBooks: GetBooks

    private val genericError = "Error"

    private val bookListViewModel: BookListViewModel by inject()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        startKoin {
            modules(
                listOf(
                    presentationModule,
                    module(override = true) {
                        factory { getBooks }
                        single(named(PresentationNaming.GENERIC_ERROR)) { genericError }
                    }
                )
            )
        }
    }

    @Test
    fun `should publish the book list`() {
        runBlocking {
            `when`(getBooks.invoke(any(), any(), any())).thenAnswer { args ->
                args.getArgument<(Either<Failure, List<BookDomainModel>>) -> Unit>(2)(
                    Right(
                        getBookList()
                    )
                )
            }

            bookListViewModel.books.value!!.forEachIndexed { index, item ->
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

    private fun getBookList() =
        (1..10).map { index ->
            BookDomainModel(
                title = "Title $index",
                author = "Author $index",
                shortSynopsis = "Short synopsis $index",
                synopsis = "Synopsis $index",
                image = "Image $index"
            )
        }

    @Test
    fun `should return an error message when fails`() {
        runBlocking {
            `when`(getBooks.invoke(any(), any(), any())).thenAnswer { args ->
                args.getArgument<(Either<Failure, List<BookDomainModel>>) -> Unit>(2)(
                    Left(
                        Failure.GenericError
                    )
                )
            }

            bookListViewModel.failure.value shouldBeEqualTo genericError
        }
    }
}