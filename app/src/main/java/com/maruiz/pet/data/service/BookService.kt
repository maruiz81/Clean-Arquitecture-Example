package com.maruiz.pet.data.service

import com.maruiz.pet.data.model.BookDataModel
import retrofit2.Call
import retrofit2.http.GET

interface BookService {
    @GET("BookList.json")
    fun getBooks(): Call<List<BookDataModel>>
}
