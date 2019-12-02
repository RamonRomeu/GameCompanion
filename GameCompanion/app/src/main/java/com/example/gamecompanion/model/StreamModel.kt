package com.example.gamecompanion.model

import com.google.gson.annotations.SerializedName

data class StreamModel(
    val id: String? =null,
    @SerializedName("user_id") val userId: String?=null,           //serialized name serveix pk no tingui que ser exactament igual que el parametre del json de twitch
    @SerializedName("user_name")val username:String?=null,
    val title:String?=null,
    @SerializedName("viewer_count")val viewerCount:String?=null,
    @SerializedName("thumbnail_url")val thumbnailUrl: String?=null

    )


data class StreamsResponse(
    val data: List<StreamModel>? = null,
    val pagination: TwitchPagination? = null

)

data class TwitchPagination(
    val cursor: String?=null

)