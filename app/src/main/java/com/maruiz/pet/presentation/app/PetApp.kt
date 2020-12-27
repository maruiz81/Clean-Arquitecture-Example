package com.maruiz.pet.presentation.app

import android.app.Application
import com.maruiz.pet.data.di.dataModule
import com.maruiz.pet.domain.di.domainModule
import com.maruiz.pet.presentation.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.core.context.startKoin

class PetApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PetApp)
            androidFileProperties()
            modules(listOf(dataModule, domainModule, presentationModule))
        }
    }
}