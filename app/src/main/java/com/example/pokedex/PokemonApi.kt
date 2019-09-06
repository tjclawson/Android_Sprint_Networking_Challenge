package com.example.pokedex

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface PokemonApi{

    @GET("v2/pokemon/{id}")
    fun getPokemonByID(@Path("id") pokemonId: String): Call<Pokemon>

    @GET("/{name}")
    fun getPokemonByName(@Path("name") pokemonName: String): Call<Pokemon>

    class Factory {
        //Can't define this in interface, so we put in class inside of interface
        companion object {
            val BASE_URL = "https://pokeapi.co/api/"
            val gson = Gson()
            val logger = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
                level = HttpLoggingInterceptor.Level.BODY
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(logger)
                .retryOnConnectionFailure(false)
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build()

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

