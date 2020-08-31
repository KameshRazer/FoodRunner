package com.example.foodrunner.ui.favouriteRestaurants

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrunner.R
import com.example.foodrunner.ui.DatabaseHandler
import com.example.foodrunner.ui.home.FavouriteRestaurants

class FavouriteFragment : Fragment(){
    private lateinit var foodList:MutableList<FavouriteRestaurants>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FavoriteAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_favourite,container,false)
        val database = DatabaseHandler(inflater.context)
        foodList = database.readData()
        recyclerView = root.findViewById(R.id.fr_recyclerview)
        adapter = FavoriteAdapter(foodList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager=LinearLayoutManager(activity)
        return root
    }
}