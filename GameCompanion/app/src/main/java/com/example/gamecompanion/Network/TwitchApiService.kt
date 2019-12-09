package com.example.gamecompanion.Network

import com.example.gamecompanion.fragment.GameModel
import com.example.gamecompanion.fragment.GameResponse
import com.example.gamecompanion.model.StreamsResponse
import com.google.android.gms.common.internal.ClientIdentity
import io.opencensus.common.ServerStatsFieldEnums

import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface TwitchApiService {

    //Endpoint /streams
    @Headers("Client-ID: $clientId")
    @GET("streams")
    fun getStreams(): retrofit2.Call<StreamsResponse>


    //Endpoint /games
    @Headers("Client-ID: $clientId")
    @GET("games")
    fun getGames(@Query("id") gameId: String): retrofit2.Call<GameResponse>



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