package com.example.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), Callback<Pokemon> {
    override fun onFailure(call: Call<Pokemon>, t: Throwable) {
        Toast.makeText(this, "Not a Valid Pokemon ID or Name", Toast.LENGTH_LONG).show()
    }

    override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
        tv_pokemon.text = response.body()?.toString()
    }

    lateinit var pokemonService: PokemonApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pokemonService = PokemonApi.Factory.create()

        button_search.setOnClickListener {
            var pokemonId = et_search.text.toString()
            getPokemonById(pokemonId)
        }
    }

    private fun getPokemonById(pokemonId: String){
        pokemonService.getPokemonByID(pokemonId).enqueue(this)
    }
}
