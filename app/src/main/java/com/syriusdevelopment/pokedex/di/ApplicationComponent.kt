package com.syriusdevelopment.pokedex.di

import android.content.Context
import com.syriusdevelopment.pokedex.PokedexApplication
import com.syriusdevelopment.pokedex.ui.pokedex.PokedexSubcomponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, SubcomponentsModule::class])
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }

    fun inject(application: PokedexApplication)
    fun pokedexSubcomponent(): PokedexSubcomponent.Factory
}