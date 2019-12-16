package com.example.gamecompanion.fragment


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gamecompanion.Network.RickAndMortyAPIService
import com.example.gamecompanion.Network.TwitchApiService
import com.example.gamecompanion.R
import com.example.gamecompanion.activity.LoginActivity
import com.example.gamecompanion.activity.RegisterActivity
import com.example.gamecompanion.model.CharactersResponse
import com.example.gamecompanion.model.StreamModel
import com.example.gamecompanion.model.StreamsResponse
import com.example.gamecompanion.model.UserModel
import com.example.gamecompanion.util.COLECTION_USERS
import com.example.gamecompanion.util.SharePreferencesManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class FriendsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friends, container, false)
    }

    //Init /Main
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        RickAndMortyAPIService.endpoints.getCharacters().enqueue(object: retrofit2.Callback<CharactersResponse>{
//            override fun onFailure(call: retrofit2.Call<CharactersResponse>, t: Throwable) {
//                //No internet
//                Log.w("FriendsFragment",t)
//            }
//
//            override fun onResponse(
//                call: retrofit2.Call<CharactersResponse>,
//                response: retrofit2.Response<CharactersResponse>
//            ) {
//                if(response.isSuccessful){ //response.code()==200
//                   val characters= response.body()?.results
//                    Log.i("FriendsFragment",characters?.toString() ?: "Null body")
//                }
//            }
//
//        })

        //GET STREAMS(COROUTINES)
        //1 - Waiting scope
        //2 - Cancellable scope tied to Fragment Lifecycle
        lifecycleScope.launch {
            Log.i("StreamsFragment", "Starting Get Streams")
            try {
                val streamsResponse = TwitchApiService.endpoints.getStreams()
                var streams = streamsResponse.data ?: emptyList()
                Log.i("StreamsFragment", "Got ${streams.count()} streams")
                Log.i("StreamsFragment", streams.toString())
                //Add Games to Streams
                //streams=addGames(streams)
                //TODO:Set to StreamAdatper
               // adapter.list = ArrayList(streams.map { it.game?.name ?: "" })
               // adapter.notifyDataSetChanged()

            }catch (e: IOException){
                //No internet
                Log.w("StreamsFragment","Request couldn't be executed")
            }catch(e: HttpException){
                //Server replied with error
                Log.w("StreamsFragment", e.message())

            }

        }

//
//        //GET STREAMS
//        //TWITCH
//        TwitchApiService.endpoints.getStreams().enqueue(object : retrofit2.Callback<StreamsResponse>{
//
//            override fun onFailure(call: retrofit2.Call<StreamsResponse>, t: Throwable) {
//                //No internet
//                Log.w("StreamsFragment",t)
//            }
//
//            override fun onResponse(call: retrofit2.Call<StreamsResponse>, response: retrofit2.Response<StreamsResponse>) {
//
//                Log.i("StreamsFragment","++ on Response ++")
//                if(response.isSuccessful){ //code ==200
//                    //All good
//                    Log.i("StreamsFragment",response.body()?.toString() ?: "Null body")
//                    val streams = response.body()?.data
//                    //iterate streams
//                    streams?.forEach {
//                        //Get Games
//                        it.gameId?.let { gameId->
//                            TwitchApiService.endpoints.getGames(gameId).enqueue(object : retrofit2.Callback<GameResponse>{
//                                override fun onFailure(
//                                    call: retrofit2.Call<GameResponse>,
//                                    t: Throwable
//                                ) {
//                                    Log.w("StreamsFragment",t)
//                                }
//
//                                override fun onResponse(
//                                    call: retrofit2.Call<GameResponse>,
//                                    response: retrofit2.Response<GameResponse>
//                                ) {
//                                    if(response.isSuccessful){
//                                        val games=response.body()?.data
//                                        //Replace gameId -> game
//                                        streams?.forEach{stream->  //PER CADA STREAM DONEM EL JOC PER EL SEU GAMEid
//                                            games?.forEach {game->
//                                                if (stream.gameId == game.id) {
//                                                    stream.game = game
//                                                }
//                                            }
//                                        }
//                                        Log.i("StreamsFragment","Got games $games")
//
//                                    }else{
//                                        Log.w("StreamsFragment","Error getting games")
//                                    }
//                                }
//
//                            })
//                        }
//
//                    }
//
//                   // response.body()?.data?.first()?.getThumbnailImageUrl()
//                } else{
//
//                    //Not OK
//                    Log.w("StreamsFragment",response.message())
//                }
//
//            }
//
//
//
//
//        })


    }



    //GAMES
//    private suspend fun addGames(streams: List<StreamModel>): List<StreamModel>{
//        //Get string list of all gameIds
//        val gameIds= streams.map{it.gameId ?: "" }
//
//        //Get Games
//        val gameResponse = TwitchApiService.endpoints.getGames(streams)
//        val games = gameResponse.data ?: emptyList()
//        //Replace gameId ->game
//        streams.forEach{stream->stream.game = games.firstOrNull {it.id== stream.gameId}}
//
//
//        return streams
//    }
//
//    private suspend fun getGames(){
//
//
//    }


}