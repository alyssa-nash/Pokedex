package com.syriusdevelopment.pokedex.ui.pokedex.list

import androidx.lifecycle.*
import com.syriusdevelopment.pokedex.data.PokemonRepository
import com.syriusdevelopment.pokedex.data.Resource
import com.syriusdevelopment.pokedex.data.model.PokemonStub
import com.syriusdevelopment.pokedex.ui.pokedex.PokedexScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@PokedexScope
class PokemonListViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {
    private val _listLiveData = MutableLiveData<Resource<List<PokemonStub>>>()
    val listLiveData: LiveData<Resource<List<PokemonStub>>>
        get() = _listLiveData

    fun getList() {
        viewModelScope.launch {
            pokemonRepository.getPokemon(0).collect {
                _listLiveData.value = it
            }
        }
    }
}