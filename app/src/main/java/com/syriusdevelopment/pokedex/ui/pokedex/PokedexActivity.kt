package com.syriusdevelopment.pokedex.ui.pokedex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.syriusdevelopment.pokedex.R

class PokedexActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
    }
}