package com.maruiz.pet.presentation.di

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.maruiz.pet.R
import com.maruiz.pet.presentation.viewmodel.BookListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val presentationModule = module {
    factory { (fragment: Fragment) -> fragment.findNavController() }

    single(named(PresentationNaming.GENERIC_ERROR)) { androidContext().resources.getString(R.string.generic_error) }

    viewModel {
        BookListViewModel(
            getBooks = get(),
            genericErrorMsg = get(named(PresentationNaming.GENERIC_ERROR))
        )
    }
}

enum class PresentationNaming {
    GENERIC_ERROR
}