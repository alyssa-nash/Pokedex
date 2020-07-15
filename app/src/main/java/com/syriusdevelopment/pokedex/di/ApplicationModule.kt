package com.syriusdevelopment.pokedex.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.syriusdevelopment.pokedex.data.local.PokemonDatabase
import com.syriusdevelopment.pokedex.data.local.PokemonStubDao
import com.syriusdevelopment.pokedex.data.remote.PokeApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
object ApplicationModule {

    @Provides
    @Singleton
    fun providesMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        return OkHttpClient.Builder()
            .readTimeout(60L, TimeUnit.SECONDS)
            .connectTimeout(60L, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun providesPokeApiService(okHttpClient: OkHttpClient, moshi: Moshi) : PokeApiService {
        return Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build().create(PokeApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesDatabase(context: Context): PokemonDatabase {
        return Room.databaseBuilder(context, PokemonDatabase::class.java, "Pokemon.db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun providesPokemonStubDao(pokemonDatabase: PokemonDatabase): PokemonStubDao =
        pokemonDatabase.pokemonStubDao()
}