package com.syriusdevelopment.pokedex.data.local

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.syriusdevelopment.pokedex.data.model.PokemonStub
import com.syriusdevelopment.pokedex.data.remote.PokeApiService

@OptIn(ExperimentalPagingApi::class)
class PokemonStubRemoteMediator(
    private val pokeApiService: PokeApiService,
    private val pokemonDatabase: PokemonDatabase
) : RemoteMediator<Int, PokemonStub>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonStub>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                    lastItem.id
                }
            }

            val response =
                pokeApiService.getPokemon(limit = state.config.pageSize, offset = loadKey)
            pokemonDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    pokemonDatabase.pokemonStubDao().clearAll()
                }

                pokemonDatabase.pokemonStubDao().insertAll(response.results)
            }

            MediatorResult.Success(endOfPaginationReached = response.results.isEmpty())
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}