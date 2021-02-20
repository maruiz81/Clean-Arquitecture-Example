package com.maruiz.pet.presentation.view.fragment

import android.os.Bundle
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.maruiz.pet.R
import com.maruiz.pet.presentation.model.BookPresentationModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.mockito.quality.Strictness

@RunWith(AndroidJUnit4::class)
class BookDetailFragmentTest : AutoCloseKoinTest() {
    @get:Rule
    val rule: MockitoRule = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS)

    @Test
    fun `load fragment with empty list`() {
        initFragment(getBookPresentationModel())

        onView(withId(R.id.title))
            .check(matches(withText(getBookPresentationModel().title)))

        onView(withId(R.id.subtitle))
            .check(matches(withText(getBookPresentationModel().subtitle)))

        onView(withId(R.id.genre))
            .check(matches(withText(getBookPresentationModel().genres)))

        onView(withId(R.id.synopsis))
            .check(matches(withText(getBookPresentationModel().synopsis)))
    }

    private fun getBookPresentationModel() =
        BookPresentationModel(
            title = "Anna karenina",
            subtitle = "Leo Tolstoi",
            shortSynopsis = "short synopsis",
            synopsis = "synopsis",
            image = "image",
            genres = "Novel"
        )

    private fun initFragment(
        argument: BookPresentationModel
    ): FragmentScenario<BookDetailFragment> {
        return launchFragmentInContainer(
            fragmentArgs = Bundle().apply { putParcelable("book", argument) },
            themeResId = R.style.Theme_MaterialComponents
        )
    }
}