package com.example.gamecompanion.model

import com.google.gson.annotations.SerializedName

//Rick y Morty
data class CharacterModel(
    val id: String? =null,
    val name: String? = null,
    val status: String? = null,
    val species:String? = null,
     val type:String? = null,
    val gender:String? = null,
    val origin: CharacterOrigin?=null
)


data class  CharacterOrigin(
    val name: String?=null,
    val url: String?=null

)

data class CharactersResponse(

    val results: List<CharacterModel>? = null

)


