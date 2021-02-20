package com.maruiz.pet.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.fix
import com.maruiz.pet.domain.usecases.GetBooks
import com.maruiz.pet.presentation.mapper.convertToPresentation
import com.maruiz.pet.presentation.model.BookPresentationModel
import com.maruiz.pet.presentation.utils.SingleLiveEvent

class BookListViewModel(
    private val getBooks: GetBooks,
    private val genericErrorMsg: String
) : ViewModel(), BookListener {
    private val _books = MutableLiveData<List<BookPresentationModel>>(emptyList())
    private val _failure = SingleLiveEvent<String>()
    private val _detail = SingleLiveEvent<BookPresentationModel>()

    val books: LiveData<List<BookPresentationModel>> = _books
    val failure: LiveData<String> = _failure
    val detail: LiveData<BookPresentationModel> = _detail

    init {
        loadBooks()
    }

    private fun loadBooks() {
        getBooks(Unit, viewModelScope) {
            it.fix().fold({ _failure.value = genericErrorMsg }) {
                _books.value = it.map { it.convertToPresentation() }
            }
        }
    }

    override fun bookClicked(book: BookPresentationModel) {
        _detail.value = book
    }
}

interface BookListener {
    fun bookClicked(book: BookPresentationModel)
}