package com.maruiz.pet.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.maruiz.pet.data.di.DataNaming
import com.maruiz.pet.data.di.dataModule
import com.maruiz.pet.data.error.Failure
import com.maruiz.pet.data.model.BookDataModel
import com.maruiz.pet.data.utils.FileReader.readStringFromFile
import com.maruiz.pet.data.utils.Method
import com.maruiz.pet.data.utils.checkMethodAndPath
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.After
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

@RunWith(JUnit4::class)
class BookRepositoryTest : AutoCloseKoinTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val okHttpClient = OkHttpClient.Builder().build()

    private val mockWebServer = MockWebServer()

    private val bookRepository: BookRepository by inject()

    @Before
    fun setUp() {
        mockWebServer.start()
        startKoin {
            modules(
                listOf(
                    dataModule,
                    module(override = true) {
                        factory { okHttpClient }
                        single(named(DataNaming.BASE_URL)) { mockWebServer.url("/") }
                    }
                )
            )
        }
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `should return the books`() {
        runBlocking {
            val response = MockResponse().setResponseCode(200)
                .setBody(readStringFromFile("jsons/books.json"))

            mockWebServer.enqueue(response)

            bookRepository.getBooks().fold({ assert(false) }) {
                it.size shouldBeEqualTo 10

                it.forEachIndexed { index, book -> testBook(book, index + 1) }
            }

            mockWebServer.takeRequest().checkMethodAndPath(Method.GET, "/BookList.json")
        }
    }

    private fun testBook(book: BookDataModel, index: Int) {
        book.run {
            bookId shouldBeEqualTo index
            title shouldBeEqualTo "Title $index"
            author shouldBeEqualTo "Author $index"
            shortSynopsis shouldBeEqualTo "Synopsis $index"
            synopsis shouldBeEqualTo "Synopsis synopsis $index"
            image shouldBeEqualTo "Image $index"
        }
    }

    @Test
    fun `should return failure when request fails`() {
        runBlocking {
            val response = MockResponse().setResponseCode(400)

            mockWebServer.enqueue(response)

            bookRepository.getBooks()
                .fold({ it shouldBeInstanceOf Failure.GenericError::class }) { assert(false) }

            mockWebServer.takeRequest().checkMethodAndPath(Method.GET, "/BookList.json")
        }
    }
}