package com.syriusdevelopment.pokedex.data.local

import androidx.paging.PagingSource
import androidx.room.*
import com.syriusdevelopment.pokedex.data.model.PokemonStub

@Dao
interface PokemonStubDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemon: List<PokemonStub>)

    @Query("SELECT * FROM PokemonStub")
    fun getPokemonList(): PagingSource<Int, PokemonStub>

    @Query("DELETE FROM PokemonStub")
    suspend fun clearAll()
}