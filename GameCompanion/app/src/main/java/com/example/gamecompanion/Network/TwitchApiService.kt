package com.example.gamecompanion.Network

import com.example.gamecompanion.model.StreamsResponse
import com.google.android.gms.common.internal.ClientIdentity
import io.opencensus.common.ServerStatsFieldEnums

import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers

interface TwitchApiService {

    @Headers("Client-ID: ywvglt0gib8rqdly0ejobehqfi071m")
    @GET("streams")
    fun getStreams(): retrofit2.Call<StreamsResponse>




    //Create https client
    companion object{
        //Http Client
        private val retrofit= Retrofit.Builder()
            .baseUrl("https://api.twitch.tv/helix/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        //Endpoints
        val endpoints= retrofit.create<TwitchApiService>(TwitchApiService::class.java)


    }
}