package com.syriusdevelopment.pokedex.data

import com.syriusdevelopment.pokedex.data.local.PokemonStubDao
import com.syriusdevelopment.pokedex.data.model.PokemonResponse
import com.syriusdevelopment.pokedex.data.model.PokemonStub
import com.syriusdevelopment.pokedex.data.remote.PokeApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepository @Inject constructor(
    private val pokeApiService: PokeApiService,
    private val pokemonStubDao: PokemonStubDao
) {

    private val limit: Int = 20

    fun getPokemon(page: Int): Flow<Resource<List<PokemonStub>>> {
        return object : NetworkBoundResource<List<PokemonStub>, PokemonResponse>() {
            override suspend fun saveRemoteData(response: PokemonResponse) {
                pokemonStubDao.insertPokemonList(response.results)
            }

            override fun getFromLocal(): Flow<List<PokemonStub>> {
                return pokemonStubDao.getPokemonListByPage(page)
            }

            override suspend fun getFromRemote(): Response<PokemonResponse> {
                return pokeApiService.getPokemon(offset = limit * page)
            }
        }.asFlow().flowOn(Dispatchers.IO)
    }
}