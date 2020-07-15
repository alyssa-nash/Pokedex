package com.syriusdevelopment.pokedex.di

import com.syriusdevelopment.pokedex.ui.pokedex.PokedexSubcomponent
import dagger.Module

@Module(subcomponents = [PokedexSubcomponent::class])
class SubcomponentsModule