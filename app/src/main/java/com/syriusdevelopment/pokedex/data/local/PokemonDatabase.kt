package com.syriusdevelopment.pokedex.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.syriusdevelopment.pokedex.data.model.PokemonStub

@Database(entities = [PokemonStub::class], version = 1)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonStubDao(): PokemonStubDao
}