package com.example.pokedex

import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApi{

    @GET("{name-id}")
    fun getPokemon(@Path("name-id") pokemonId: String): Call<Pokemon>

    class Factory {

        companion object {
            val BASE_URL = "https://pokeapi.co/api/v2/pokemon/"
            val gson = Gson()

            fun create(): PokemonApi {

                return Retrofit.Builder()
                    .baseUrl(PokemonApi.Factory.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
                    .create(PokemonApi::class.java)
            }
        }
    }
}