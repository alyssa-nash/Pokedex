package com.syriusdevelopment.pokedex.ui.pokedex.list

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.syriusdevelopment.pokedex.data.PokemonRepository
import com.syriusdevelopment.pokedex.data.model.PokemonStub
import kotlinx.coroutines.flow.Flow

class PokemonListViewModel (private val pokemonRepository: PokemonRepository) : ViewModel() {

    private var _listResults: Flow<PagingData<PokemonStub>>? = null

    fun getList(): Flow<PagingData<PokemonStub>> {
        val newResults: Flow<PagingData<PokemonStub>> = pokemonRepository.getPokemon()
            .cachedIn(viewModelScope)
        _listResults = newResults
        return newResults
    }
}