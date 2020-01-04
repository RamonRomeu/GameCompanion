package com.example.gamecompanion.fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gamecompanion.Network.TwitchApiService
import com.example.gamecompanion.R
import com.example.gamecompanion.adapter.StreamsAdapter
import com.example.gamecompanion.model.StreamModel
import kotlinx.android.synthetic.main.fragment_streams.*
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class StreamsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_streams, container, false)
    }

    //Init /Main
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = StreamsAdapter(ArrayList())
        recyclerview.layoutManager = GridLayoutManager(requireContext(),2)
        recyclerview.adapter=adapter


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

                streams=addGames(streams)
                //TODO:Set to StreamAdatper
                adapter.list = ArrayList(streams)
                adapter.notifyDataSetChanged()

            }catch (e: IOException){
                //No internet
                Log.w("StreamsFragment","Request couldn't be executed")
            }catch(e: HttpException){
                //Server replied with error
                Log.w("StreamsFragment", e.message())

            }

        }


    }



    private suspend fun addGames(streams: List<StreamModel>): List<StreamModel>{
        //Get string list of all gameIds
        val gameIds= streams.map{it.gameId ?: "" }

        //Get Games
        val gamesResponse = TwitchApiService.endpoints.getGames(gameIds)
        val games = gamesResponse.data ?: emptyList()
        //Replace gameId ->game
        streams.forEach{stream->stream.game = games.firstOrNull {it.id== stream.gameId}}
        return streams
    }



}