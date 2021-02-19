package com.maruiz.pet.data.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.maruiz.pet.BuildConfig
import com.maruiz.pet.data.repository.BookRepository
import com.maruiz.pet.data.service.BookService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val dataModule = module {

    val BASE_URL_KEY = "base_url"

    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    single(named(DataNaming.BASE_URL)) { getProperty(BASE_URL_KEY).toHttpUrl() }

    single {
        val loggingInterceptor: HttpLoggingInterceptor =
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

        OkHttpClient.Builder()
            .addInterceptor(ChuckerInterceptor.Builder(androidContext()).build())
            .let { if (BuildConfig.DEBUG) it.addInterceptor(loggingInterceptor) else it }
            .build()
    }

    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl(get<HttpUrl>(named(DataNaming.BASE_URL)))
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }

    single { get<Retrofit>().create(BookService::class.java) }

    single<BookRepository> { BookRepository.Network(bookService = get()) }
}

enum class DataNaming {
    BASE_URL
}