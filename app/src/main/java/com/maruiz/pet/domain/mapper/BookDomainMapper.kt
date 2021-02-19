package com.maruiz.pet.domain.mapper

import com.maruiz.pet.data.model.BookDataModel
import com.maruiz.pet.domain.model.BookDomainModel

fun BookDataModel.convertToDomain(): BookDomainModel =
    BookDomainModel(
        title = title,
        author = author,
        shortSynopsis = shortSynopsis,
        synopsis = synopsis,
        image = image,
        published = published.toString(),
        genres = genres
    )