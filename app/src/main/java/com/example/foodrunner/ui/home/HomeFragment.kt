package com.example.foodrunner.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrunner.MyMessenger
import com.example.foodrunner.R
import org.json.JSONArray
import org.json.JSONObject
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HomeDataAdapter
//    private lateinit var logInfo:SharedPreferences
    private  var foodList : ArrayList<ArrayList<String>> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
//        logInfo = activity?.getSharedPreferences("LoginInfo", Context.MODE_PRIVATE)!!
        recyclerView = root.findViewById(R.id.home_recyclerview)
        adapter = HomeDataAdapter(foodList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        return root
    }

    @SuppressLint("CommitPrefEdits")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(foodList.size == 0)
            startRequest()
    }

     private fun startRequest(){

         val url = "http://13.235.250.119/v2/restaurants/fetch_result"

         var jsonData: JSONObject = MyMessenger().sendGETRequest(url)
         jsonData  = JSONObject(jsonData.get("data").toString())
         val isSuccess = (jsonData.getString("success") == "true")
         if(isSuccess) {
             val jsonArray: JSONArray = jsonData.getJSONArray("data")
             val size = jsonArray.length()
             for (i in 0 until size) {
                 val list = ArrayList<String>(4)
                 val data: JSONObject = jsonArray.getJSONObject(i)
                 list.add(data.getString("name"))
                 list.add(data.getString("image_url"))
                 list.add(data.getString("rating"))
                 list.add(data.getString("cost_for_one"))
                 list.add(data.getString("id"))
                 foodList.add(list)
             }
         }

     }
}
