package com.example.pokedex

import com.google.gson.annotations.SerializedName

data class Pokemon(val name: String,
                   @SerializedName("front_default")
                   val spriteUrl: String,
                   val id: String,
                   val abilities: List<String>,
                   val types: List<String>)