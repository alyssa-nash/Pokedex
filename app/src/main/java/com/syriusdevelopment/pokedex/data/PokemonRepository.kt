package com.syriusdevelopment.pokedex.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.syriusdevelopment.pokedex.data.local.PokemonDatabase
import com.syriusdevelopment.pokedex.data.local.PokemonStubDao
import com.syriusdevelopment.pokedex.data.local.PokemonStubRemoteMediator
import com.syriusdevelopment.pokedex.data.model.PokemonStub
import com.syriusdevelopment.pokedex.data.remote.PokeApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class PokemonRepository @Inject constructor(
    private val pokeApiService: PokeApiService,
    private val pokemonDatabase: PokemonDatabase
) {

    private val pokemonStubDao: PokemonStubDao = pokemonDatabase.pokemonStubDao()

    fun getPokemon(): Flow<PagingData<PokemonStub>> {
        val pagingSourceFactory = { pokemonStubDao.getPokemonList() }
        return Pager(
            config = PagingConfig(
                pageSize = LIMIT,
                enablePlaceholders = true
            ),
            remoteMediator = PokemonStubRemoteMediator(
                pokeApiService,
                pokemonDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    companion object {
        const val LIMIT: Int = 20
    }
}