package com.example.gamecompanion.Network

import com.example.gamecompanion.model.GameResponse
import com.example.gamecompanion.model.StreamsResponse

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface TwitchApiService {

    //Endpoint /streams
    @Headers("Client-ID: $clientId")
    @GET("streams?game_id=491115")
    suspend fun getStreams(): StreamsResponse


    //Endpoint /games
    @Headers("Client-ID: $clientId")
    @GET("games")
    suspend fun getGames(@Query("id") gameId: List<String>): GameResponse



    //Create https client
    companion object{

        private const val clientId= "ywvglt0gib8rqdly0ejobehqfi071m"

        //Http Client
        private val retrofit= Retrofit.Builder()
            .baseUrl("https://api.twitch.tv/helix/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        //Endpoints
        val endpoints= retrofit.create<TwitchApiService>(TwitchApiService::class.java)


    }
}