package com.example.gamecompanion.Network


import com.example.gamecompanion.model.CharactersResponse
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RickAndMortyAPIService{
    //Endpoints
    @GET("character")
    fun getCharacters(): retrofit2.Call<CharactersResponse>


    companion object{
        private val clientHttp = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        val endpoints = clientHttp.create(RickAndMortyAPIService::class.java)


    }

}