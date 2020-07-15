package com.syriusdevelopment.pokedex.ui.pokedex.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.syriusdevelopment.pokedex.data.PokemonRepository
import javax.inject.Inject

class PokemonListViewModelFactory @Inject constructor(private val pokemonRepository: PokemonRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(PokemonRepository::class.java).newInstance(pokemonRepository)
    }
}