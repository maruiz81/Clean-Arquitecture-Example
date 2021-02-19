package com.maruiz.pet.presentation.view.fragment

import android.os.Bundle
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.maruiz.pet.R
import com.maruiz.pet.presentation.model.BookPresentationModel
import com.maruiz.pet.presentation.viewmodel.BookListViewModel
import com.spendit.myspenditcard.presentation.fragment.recyclerview.RecyclerViewInteraction
import com.spendit.myspenditcard.presentation.fragment.recyclerview.RecyclerViewItemCountAssertion
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.mockito.quality.Strictness

@RunWith(AndroidJUnit4::class)
class BookListFragmentTest : AutoCloseKoinTest() {
    @get:Rule
    val rule: MockitoRule = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS)

    @Mock
    private lateinit var bookListViewModel: BookListViewModel

    private val errorMessage = "Error"

    @Before
    fun setUp() {
        loadKoinModules(
            module(override = true) {
                factory { bookListViewModel }
            }
        )
    }

    @Test
    fun `load fragment with empty list`() {
        initFragment(booksLiveData = MutableLiveData(emptyList()))
    }

    @Test
    fun `load fragment with a list of books`() {
        val books = getBooks()
        val booksLiveData = MutableLiveData(books)
        initFragment(booksLiveData = booksLiveData)

        onView(withId(R.id.recyclerView))
            .check(RecyclerViewItemCountAssertion(10))

        RecyclerViewInteraction(withId(R.id.recyclerView), books)
            .check { book, view, exception ->
                matches(withText(book.title))
                    .check(view.findViewById(R.id.title), exception)
                matches(withText(book.author))
                    .check(view.findViewById(R.id.author), exception)
                matches(withText(book.shortSynopsis))
                    .check(view.findViewById(R.id.synopsis), exception)
            }
    }

    @Test
    fun `Error loading books`() {
        val failureLiveData = MutableLiveData(errorMessage)
        initFragment(failureLiveData = failureLiveData)

        onView(withId(R.id.recyclerView))
            .check(RecyclerViewItemCountAssertion(0))

        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(errorMessage)))
    }

    private fun getBooks() =
        (1..10).map {
            BookPresentationModel(
                title = "Title $it",
                author = "Author $it",
                shortSynopsis = "Short synopsis $it",
                synopsis = "Synopsis $it",
                image = "Image $it"
            )
        }

    private fun initFragment(
        booksLiveData: LiveData<List<BookPresentationModel>> = MutableLiveData(emptyList()),
        failureLiveData: MutableLiveData<String> = MutableLiveData()
    ): FragmentScenario<BookListFragment> {
        `when`(bookListViewModel.books).thenReturn(booksLiveData)
        `when`(bookListViewModel.failure).thenReturn(failureLiveData)

        return launchFragmentInContainer(
            fragmentArgs = Bundle(),
            themeResId = R.style.Theme_MaterialComponents
        )
    }
}