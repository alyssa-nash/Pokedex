package com.syriusdevelopment.pokedex.ui.pokedex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.syriusdevelopment.pokedex.R
import com.syriusdevelopment.pokedex.app

class PokedexActivity : AppCompatActivity() {

    lateinit var pokedexSubcomponent: PokedexSubcomponent

    override fun onCreate(savedInstanceState: Bundle?) {
        pokedexSubcomponent = app.applicationComponent.pokedexSubcomponent().create()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
    }
}