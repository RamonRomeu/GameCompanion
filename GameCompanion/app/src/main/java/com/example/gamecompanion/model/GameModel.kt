package com.example.gamecompanion.model

import com.google.gson.annotations.SerializedName

data class GameModel(
    val id:String?=null,
    val name:String?=null,
    @SerializedName("box_art_url") val imageUrl:String?=null

)

data class GameResponse(
    val data:List<GameModel>?=null

)