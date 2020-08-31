package com.example.foodrunner.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrunner.HomeActivity
import com.example.foodrunner.R
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Headers
import okhttp3.OkHttpClient
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.lang.Exception
import java.util.*
import java.util.concurrent.CountDownLatch
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.math.log
import kotlin.random.Random.Default.Companion

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
        recyclerView.setOnClickListener(View.OnClickListener { println("Recycle click") })
    }

     private fun startRequest(){
//         println("Request Start Here...")

        val client = OkHttpClient()
        var url = "http://13.235.250.119/v2/restaurants/fetch_result"
        val countDownLatch = CountDownLatch(1)
        val headers  = Headers.of(getHeader())

        try {
            val request = okhttp3.Request.Builder().url(url)
                .headers(headers)
                .build()
//            foodList.clear()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    println("Failure")
                    println(e.message.toString())
                    countDownLatch.countDown()
                }

                @SuppressLint("WrongConstant")
                override fun onResponse(call: Call, response: okhttp3.Response) {
                    val str_response = response.body()!!.string()
//                    println(str_response)
                    val json_data: JSONObject = JSONObject(str_response)
                    val json_final : JSONObject = JSONObject(json_data.get("data").toString())
                    val json_array: JSONArray = json_final.getJSONArray("data")
                    val size = json_array.length()

                    for (i in 0..size - 1) {

                        val list = ArrayList<String>(4)
                        val data: JSONObject = json_array.getJSONObject(i)
                        list.add(data.getString("name"))
                        list.add(data.getString("image_url"))
                        list.add(data.getString("rating"))
                        list.add(data.getString("cost_for_one"))
                        foodList.add(list)
                    }
                    countDownLatch.countDown()
                }
            })
            countDownLatch.await()
        }catch (e: Exception){
            println("Catch: "+e.message)
        }
//         println("Request Result : ${Arrays.toString(foodList.toArray())}")
//         println("End Request")
    }
    private fun getHeader():MutableMap<String,String>{
        val header = HashMap<String,String>()
        header["Content-type"] = "application/json"
        header["token"] = "9bf534118365f1"
        return header
    }
}
