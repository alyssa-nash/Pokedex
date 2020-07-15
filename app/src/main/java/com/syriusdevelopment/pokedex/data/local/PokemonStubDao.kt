package com.syriusdevelopment.pokedex.data.local

import androidx.room.*
import com.syriusdevelopment.pokedex.data.model.PokemonStub
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonStubDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemonList(pokemonList: List<PokemonStub>)

    @Query("SELECT * FROM PokemonStub WHERE page = :page")
    fun getPokemonListByPage(page: Int): Flow<List<PokemonStub>>
}