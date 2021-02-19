package com.maruiz.pet.data.model

import com.squareup.moshi.Json

data class BookDataModel(
    val bookId: Int = 0,
    val title: String = "",
    val author: String = "",
    @Json(name = "short_synopsis") val shortSynopsis: String = "",
    val synopsis: String = "",
    val image: String = "",
    @Json(name = "first_published") val published: Int,
    val genres: List<String> = emptyList()
)
