package com.maruiz.pet.domain.model

data class BookDomainModel(
    val title: String,
    val author: String,
    val shortSynopsis: String,
    val synopsis: String,
    val image: String,
    val published: String,
    val genres: List<String>
)