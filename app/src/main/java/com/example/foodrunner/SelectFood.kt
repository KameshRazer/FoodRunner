package com.example.foodrunner

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONObject
import org.w3c.dom.Text

class SelectFood : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_food)

        val recyclerView = findViewById<RecyclerView>(R.id.sf_recyclerview)
        val foodList =ArrayList<ArrayList<String>>(1)
        val url = "http://13.235.250.119/v2/restaurants/fetch_result/5"
        var response = MyMessenger().sendGETRequest(url)
        response = JSONObject(response.getString("data"))
        val isSuccess = (response.getString("success") == "true")
        if(isSuccess){
            val arrayFood :JSONArray = response.getJSONArray("data")
            val size = arrayFood.length()
            for(i in 0 until size-1){
                val list = ArrayList<String>(4)
                val data :JSONObject = arrayFood.getJSONObject(i)
                list.add(data.getString("id"))
                list.add(data.getString("name"))
                list.add(data.getString("cost_for_one"))
                list.add(data.getString("restaurant_id"))
                foodList.add(list)
            }
        }else{
            val list = ArrayList<String>(4)
            list.add("Error")
            list.add("Error")
            list.add("Error")
            list.add("Error")
            foodList.add(list)
        }
        val adapter = SelectFoodAdapter(foodList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

//  Adapter of Food selection recycler view
    class SelectFoodAdapter(private val foodList: ArrayList<ArrayList<String>>):RecyclerView.Adapter<SelectFoodAdapter.ViewHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_display_food,parent,false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return foodList.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bindItems(foodList[position],position)
        }
        class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
            val foodName = itemView.findViewById<TextView>(R.id.rvdf_food_name)
            val s_no = itemView.findViewById<TextView>(R.id.rvdf_s_no)
            val cost = itemView.findViewById<TextView>(R.id.rvdf_cost)
            val button = itemView.findViewById<Button>(R.id.rvdf_button)

            @SuppressLint("SetTextI18n")
            fun bindItems(data:ArrayList<String>, position:Int){
                s_no.text = (position+1).toString()
                foodName.text = data[1]
                cost.text = "Rs. "+data[2]
            }
        }

    }
}
