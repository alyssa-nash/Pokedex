package com.syriusdevelopment.pokedex.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity
data class PokemonStub(
    @field:Json(name = "name") val name: String,
    @field:Json(name = "url") val url: String,
    @PrimaryKey val id: Int = url.split("/".toRegex()).dropLast(1).last().toInt(),
    val page: Int = id / 20
) {

    fun getImageUrl(): String {
        return "https://pokeres.bastionbot.org/images/pokemon/$id.png"
    }
}