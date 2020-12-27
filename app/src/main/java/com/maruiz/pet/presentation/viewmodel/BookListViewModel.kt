package com.maruiz.pet.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.fix
import com.maruiz.pet.domain.usecases.GetBooks
import com.maruiz.pet.presentation.model.BookPresentationModel

class BookListViewModel(
    private val getBooks: GetBooks,
    private val genericErrorMsg: String
) : ViewModel() {
    private val _books = MutableLiveData<List<BookPresentationModel>>(emptyList())
    private val _failure = MutableLiveData<String>()

    val books: LiveData<List<BookPresentationModel>> = _books
    val failure: LiveData<String> = _failure

    init {
        loadBooks()
    }

    private fun loadBooks() {
        getBooks(Unit, viewModelScope) {
            it.fix().fold({ _failure.value = genericErrorMsg }) {
                _books.value = it.map {
                    BookPresentationModel(
                        title = it.title,
                        author = it.author,
                        shortSynopsis = it.shortSynopsis,
                        synopsis = it.synopsis,
                        image = it.image
                    )
                }
            }
        }
    }
}