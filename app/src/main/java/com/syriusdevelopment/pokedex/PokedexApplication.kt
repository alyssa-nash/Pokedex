package com.syriusdevelopment.pokedex

import android.app.Activity
import android.app.Application
import com.syriusdevelopment.pokedex.di.ApplicationComponent
import com.syriusdevelopment.pokedex.di.DaggerApplicationComponent

class PokedexApplication : Application() {

    val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        applicationComponent.inject(this)
    }
}

val Activity.app: PokedexApplication
    get() = application as PokedexApplication