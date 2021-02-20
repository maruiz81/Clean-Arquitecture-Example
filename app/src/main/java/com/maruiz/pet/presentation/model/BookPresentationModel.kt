package com.maruiz.pet.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookPresentationModel(
    val title: String,
    val subtitle: String,
    val shortSynopsis: String,
    val synopsis: String,
    val image: String,
    val genres: String
) : Parcelable
