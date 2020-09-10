package com.example.foodrunner.ui.orderHistory

import android.content.Context
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
import java.lang.Exception

class OrderHistoryFragment: Fragment() {
    private lateinit var orderedHistory : ArrayList<ArrayList<String>>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        orderedHistory = ArrayList(1)
        val view = inflater.inflate(R.layout.fragment_order_history,container,false)
        val logInfo = activity?.getSharedPreferences("LoginInfo", Context.MODE_PRIVATE)!!
        val recyclerView = view.findViewById<RecyclerView>(R.id.oh_recyclerview)
        val adapter = OrderHistoryAdapter(orderedHistory)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)

        val userId = logInfo.getString("userId","0")
        val url = "http://13.235.250.119/v2/orders/fetch_result/$userId"
        var response = MyMessenger().sendGETRequest(url)

        response = JSONObject(response.getString("data"))
        val isSuccess = (response.getString("success") == "true")
        if(isSuccess){
//            println(response.getString("data"))
            val restaurantArray :JSONArray = response.getJSONArray("data")
            for (i in 0 until restaurantArray.length()){
//                println(restaurantArray.getString(i))
                val data = JSONObject(restaurantArray[i].toString())
                val list = ArrayList<String>(1)
                list.add(data.getString("restaurant_name"))
                list.add(data.getString("total_cost"))
                list.add(data.getString("food_items"))
                val t = JSONArray(list[2])
                try{
                    println("json -> $t")
//                    println("len -> ${t.length()}")
                 for(j in 0 until t.length())
                println("$j -> ${t.get(j).toString()}")
                }catch (e :Exception){println(e.message)}
//                val arr = t.getJSONArray()
//                println(JSONArray(list[2]))
                orderedHistory.add(list)
            }
            adapter.notifyDataSetChanged()
        }
        return view
    }
}