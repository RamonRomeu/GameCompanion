package com.example.gamecompanion.model

import com.google.gson.annotations.SerializedName

data class StreamModel(
    val id: String? =null,
    @SerializedName("user_id") val userId: String?=null,           //serialized name serveix pk no tingui que ser exactament igual que el parametre del json de twitch
    @SerializedName("game_id") val gameId: String?=null,
    @SerializedName("user_name")val username:String?=null,
    val title:String?=null,
    @SerializedName("viewer_count")val viewerCount:String?=null,
    @SerializedName("thumbnail_url")val thumbnailUrl: String?=null

    ){
        var game: GameModel?=null


        fun getThumbnailImageUrl( width:Int = 1280,height:Int =720): String?{
           return thumbnailUrl?.replace("{width}",width.toString())?.replace("{height}",height.toString())

        }


    }


data class StreamsResponse(
    val data: List<StreamModel>? = null,
    val pagination: TwitchPagination? = null

)

data class TwitchPagination(
    val cursor: String?=null

)