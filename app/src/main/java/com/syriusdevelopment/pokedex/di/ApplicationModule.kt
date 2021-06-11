package com.syriusdevelopment.pokedex.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.syriusdevelopment.pokedex.data.PokemonRepository
import com.syriusdevelopment.pokedex.data.local.PokemonDatabase
import com.syriusdevelopment.pokedex.data.local.PokemonStubDao
import com.syriusdevelopment.pokedex.data.remote.PokeApiService
import com.syriusdevelopment.pokedex.ui.pokedex.list.PokemonListViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {
    single { providesMoshi() }
    single { providesOkHttpClient() }
    single { providesPokeApiService(get(), get()) }
    single { providesDatabase(androidContext()) }
    single { providesPokemonStubDao(get()) }
    single { PokemonRepository(get(), get()) }

    viewModel {
        PokemonListViewModel(get())
    }
}

fun providesMoshi(): Moshi {
    return Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
}

fun providesOkHttpClient(): OkHttpClient {
    val logging = HttpLoggingInterceptor()
    logging.setLevel(HttpLoggingInterceptor.Level.BASIC)

    return OkHttpClient.Builder()
        .readTimeout(60L, TimeUnit.SECONDS)
        .connectTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(logging)
        .build()
}

fun providesPokeApiService(okHttpClient: OkHttpClient, moshi: Moshi): PokeApiService {
    return Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(okHttpClient)
        .build().create(PokeApiService::class.java)
}

fun providesDatabase(context: Context): PokemonDatabase {
    return Room.databaseBuilder(context, PokemonDatabase::class.java, "Pokemon.db")
        .fallbackToDestructiveMigration()
        .allowMainThreadQueries()
        .build()
}

fun providesPokemonStubDao(pokemonDatabase: PokemonDatabase): PokemonStubDao =
    pokemonDatabase.pokemonStubDao()