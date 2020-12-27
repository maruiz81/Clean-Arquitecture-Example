package com.maruiz.pet.domain.di

import com.maruiz.pet.domain.usecases.GetBooks
import org.koin.dsl.module

val domainModule = module {
    factory { GetBooks(bookRepository = get()) }
}