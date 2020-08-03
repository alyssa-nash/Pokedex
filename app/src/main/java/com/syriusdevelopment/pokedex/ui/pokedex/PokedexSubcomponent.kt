package com.syriusdevelopment.pokedex.ui.pokedex

import com.syriusdevelopment.pokedex.ui.pokedex.list.PokemonListFragment
import dagger.Subcomponent

@PokedexScope
@Subcomponent
interface PokedexSubcomponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): PokedexSubcomponent
    }

    fun inject(pokemonListFragment: PokemonListFragment)
}