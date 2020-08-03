package com.syriusdevelopment.pokedex.ui.pokedex.list

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.syriusdevelopment.pokedex.data.PokemonRepository
import com.syriusdevelopment.pokedex.data.model.PokemonStub
import com.syriusdevelopment.pokedex.ui.pokedex.PokedexScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@PokedexScope
class PokemonListViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    private var _listResults: Flow<PagingData<PokemonStub>>? = null

    fun getList(): Flow<PagingData<PokemonStub>> {
        val newResults: Flow<PagingData<PokemonStub>> = pokemonRepository.getPokemon()
            .cachedIn(viewModelScope)
        _listResults = newResults
        return newResults
    }
}