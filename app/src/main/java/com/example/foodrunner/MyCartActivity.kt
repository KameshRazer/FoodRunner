package com.example.foodrunner

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject
import org.w3c.dom.Text
import java.lang.Exception

class MyCartActivity : AppCompatActivity() {
    private lateinit var orderedFood : ArrayList<ArrayList<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_cart)
        val recyclerview = findViewById<RecyclerView>(R.id.mc_recyclerview)
        val confirmOrder = findViewById<TextView>(R.id.mc_confirm_order)
//        val data = intent.getStringExtra("orderedFood")
        val data ="{\"0\":[\"Kachaa Aloo Tadka\",\"6000\"],\"1\":[\"Bhajia Tadka\",\"60\"],\"2\":[\"Mirchi Tadka\",\"50\"]}"
        orderedFood = ArrayList(1)
        println(data)
        val food = JSONObject(data)
        val size = food.length()
        var amt =0
        for(i in 0 until size){
            val food = food.getJSONArray(i.toString())
            println(food.getString(0))
            val data = ArrayList<String>(3)
            data.add(food.getString(0))
            data.add(food.getString(1))
            amt += food.getString(1).toInt()
            orderedFood.add(data)
        }
        confirmOrder.text = "Place Order (Total Rs. $amt)"
        recyclerview.adapter = MycartAdapter(orderedFood)
        recyclerview.layoutManager = LinearLayoutManager(this)



    }
    class MycartAdapter(private val orderedList: ArrayList<ArrayList<String>>):RecyclerView.Adapter<MycartAdapter.ViewHolder>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_display_mycart, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return orderedList.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bindItems(orderedList[position])
        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            @SuppressLint("SetTextI18n")
            fun bindItems(data: ArrayList<String>) {
                val foodName = itemView.findViewById<TextView>(R.id.rvdm_food_name)
                val cost = itemView.findViewById<TextView>(R.id.rvdm_cost)
                foodName.text = data[0]
                cost.text = data[1]

            }
        }
    }
}
