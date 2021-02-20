package com.maruiz.pet.presentation.mapper

import com.maruiz.pet.domain.model.BookDomainModel
import com.maruiz.pet.presentation.model.BookPresentationModel

fun BookDomainModel.convertToPresentation(): BookPresentationModel =
    BookPresentationModel(
        title = title,
        subtitle = "$author ($published)",
        shortSynopsis = shortSynopsis,
        synopsis = synopsis,
        image = image,
        genres = genres.reduceRight { acc, genre -> "$acc, $genre" }
    )