package com.example.gamecompanion.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gamecompanion.R
import com.example.gamecompanion.activity.NewsDetailsActivity
import com.example.gamecompanion.activity.RegisterActivity
import com.example.gamecompanion.adapter.NewsAdapter
import com.example.gamecompanion.model.NewsModel
import com.example.gamecompanion.util.COLECTION_NEWS
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.fragment_news.indeterminateBar
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.item_news.*
import kotlinx.android.synthetic.main.item_news.view.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class NewsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    //Init /Main
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        indeterminateBar.visibility= View.VISIBLE
        // Set Layout Type
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Create Adapter
        val newsAdapter = NewsAdapter()

        FirebaseFirestore.getInstance()
            .collection(COLECTION_NEWS)
            .get()
            .addOnSuccessListener { document ->

                val newsList = document.toObjects(NewsModel::class.java)
                newsAdapter.list = ArrayList(newsList.toList())
                // RecyclerView <> Adapter
                recyclerView.adapter = newsAdapter
                indeterminateBar.visibility= View.GONE
            }
    }

    override fun onResume() {
        super.onResume()
        //initUI()
    }


}