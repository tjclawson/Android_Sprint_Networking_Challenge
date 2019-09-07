package com.example.pokedex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), Callback<Pokemon> {

    companion object{
        const val KEY = "INTENT_KEY"
    }

    lateinit var pokemonService: PokemonApi
    lateinit var newPokemon: Pokemon
    private var count = 0

    override fun onFailure(call: Call<Pokemon>, t: Throwable) {
        Toast.makeText(this, "Request Failed", Toast.LENGTH_LONG).show()
        Log.i("FAILURE", t.toString())
    }

    override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
        if(response.isSuccessful) {

            if(count != 0) {
                count = PokeDex.pokeDex.size
            }
            newPokemon = Pokemon(response.body()?.name,
                                response.body()?.sprites,
                                response.body()?.id,
                                response.body()?.abilities,
                                response.body()?.types,
                                count)

            PokeDex.pokeDex.add(newPokemon)
            Toast.makeText(this, "New Pokemon Added", Toast.LENGTH_LONG).show()

            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(KEY, newPokemon.index)
            startActivity(intent)
            recycler_view.adapter?.notifyDataSetChanged()

            count++

        } else {
            Toast.makeText(this, "Not a Valid Pokemon ID or Name", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pokemonService = PokemonApi.Factory.create()

        button_search.setOnClickListener {
            var pokemonId = et_search.text.toString()
            getPokemon(pokemonId)
        }

        recycler_view.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = PokemonAdapter(PokeDex.pokeDex)
        }
    }

    private fun getPokemon(pokemonId: String){
        pokemonService.getPokemon(pokemonId).enqueue(this)
    }
}