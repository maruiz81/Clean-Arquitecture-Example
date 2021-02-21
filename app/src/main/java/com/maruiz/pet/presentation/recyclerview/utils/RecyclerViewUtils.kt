package com.maruiz.pet.presentation.recyclerview.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.maruiz.pet.presentation.model.BookPresentationModel
import com.maruiz.pet.presentation.recyclerview.adapter.BookAdapter
import com.maruiz.pet.presentation.viewmodel.BookListener

@BindingAdapter(value = ["set_books", "click_listener"])
fun RecyclerView.setUpList(
    books: List<BookPresentationModel>,
    bookListener: BookListener
) {
    val bookAdapter = BookAdapter().apply {
        renderables = books
        bookClickListener = bookListener
    }

    layoutManager = GridLayoutManager(context, 3)
    adapter = bookAdapter
}


@BindingAdapter(value = ["set_image"])
fun ImageView.setImage(imageUrl: String) {
    load(imageUrl)
}