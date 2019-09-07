package com.example.pokedex

import java.io.Serializable

//looking at quicktype.io, need class for the ability/type list, and then a class for the names of the type
class Ability(val name: String)

class Abilities(val ability: Ability)

class Type(val name: String)

class Types(val type: Type)

class Sprites(val front_shiny: String)

class Pokemon(
            var name: String?,
            var sprites: Sprites?,
            var id: Int?,
            var abilities: List<Abilities>?,
            var types: List<Types>?,
            var index: Int): Serializable

object PokeDex {
    val pokeDex = mutableListOf<Pokemon>()
}