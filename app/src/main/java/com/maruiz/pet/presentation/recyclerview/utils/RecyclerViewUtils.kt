package com.maruiz.pet.presentation.recyclerview.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.maruiz.pet.presentation.model.BookPresentationModel
import com.maruiz.pet.presentation.recyclerview.adapter.BookAdapter

@BindingAdapter(value = ["set_books"])
fun RecyclerView.setBooks(books: List<BookPresentationModel>) {
    val bookAdapter = BookAdapter().apply {
        renderables = books
    }

    layoutManager = LinearLayoutManager(context)
    adapter = bookAdapter
}


@BindingAdapter(value = ["set_image"])
fun ImageView.setImage(imageUrl: String) {
    load(imageUrl)
}