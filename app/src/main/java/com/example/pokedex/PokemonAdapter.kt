package com.example.pokedex

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.DetailActivity.Companion.KEY
import kotlinx.android.synthetic.main.pokemon_list_view.view.*

class PokemonAdapter (val pokeList: MutableList<Pokemon>): RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.pokemon_list_view, parent, false) as View)
    }

    override fun getItemCount(): Int {
        return pokeList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemon = pokeList[position]
        holder.pokemonNameView.text = pokemon.name
        holder.pokemonCard.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(KEY, position)
            (context as Activity).startActivity(intent)
        }

        holder.pokemonCard.setOnLongClickListener {
            val builder = AlertDialog.Builder(context)

            builder.setTitle("Delete Item")
            builder.setMessage("Are you sure you want to delete this Pokemon from your PokeDex?")
            builder.setPositiveButton("YES"){ dialog, which ->
                PokeDex.pokeDex.removeAt(position)
                for(i in 0 until PokeDex.pokeDex.size) {
                    PokeDex.pokeDex[i].index = i
                }
                notifyDataSetChanged()
            }

            builder.setNegativeButton("No"){_, _ -> }
            val dialog: AlertDialog = builder.create()
            dialog.show()

            true
        }
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val pokemonNameView: TextView = view.tv_card
        val pokemonCard: CardView = view.cv_parent
    }
}