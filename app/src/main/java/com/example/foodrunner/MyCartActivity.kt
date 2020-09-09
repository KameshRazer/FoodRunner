package com.example.foodrunner

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.ktor.http.LinkHeader
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import org.w3c.dom.Text
import java.lang.Exception

class MyCartActivity : AppCompatActivity() {
    private lateinit var orderedFood : ArrayList<ArrayList<String>>
    private lateinit var layoutFinish : RelativeLayout

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_cart)
        val recyclerview = findViewById<RecyclerView>(R.id.mc_recyclerview)
        val confirmOrder = findViewById<TextView>(R.id.mc_confirm_order)
        val backIcon = findViewById<ImageView>(R.id.mc_icon_back)
        val cardOk = findViewById<CardView>(R.id.mc_card_ok)
        layoutFinish = findViewById(R.id.mc_layout_finish_order)
        orderedFood = ArrayList(1)

        val url ="http://13.235.250.119/v2/place_order/fetch_result/"
        val logInfo = getSharedPreferences("LoginInfo", Context.MODE_PRIVATE)
        val userId = logInfo.getString("userId","0")
        val data = intent.getStringExtra("orderedFood")
        val resId = intent.getStringExtra("resId")

        val requestData = JSONObject()
        requestData.put("user_id",userId)
        requestData.put("restaurant_id",resId)

        val foodItemArray = JSONArray()
        val food = JSONObject(data)
        var amt =0

        for(i in 0 until food.length()){
            val foodArr = food.getJSONArray(i.toString())
            val data = ArrayList<String>(3)
            data.add(foodArr.getString(0))
            data.add(foodArr.getString(1))
            amt += foodArr.getString(1).toInt()
            orderedFood.add(data)
            val temp = JSONObject()
            temp.put("food_item_id",foodArr.getString(2))
            foodItemArray.put(temp)
        }

        requestData.put("total_cost",amt)
        requestData.put("food",foodItemArray)

        confirmOrder.text = "Place Order (Total Rs. $amt)"
        recyclerview.adapter = MycartAdapter(orderedFood)
        recyclerview.layoutManager = LinearLayoutManager(this)


        confirmOrder.setOnClickListener{
            val json = MediaType.parse("application/json;charset*utf-8")
//            val reqBody = RequestBody.create(json, requestData.toString())
//            var response = JSONObject(MyMessenger().sendPOSTRequest(url,reqBody))
//            println("MyCart : ${response.toString()}")
//            response = JSONObject(response.get("data").toString())
//            val isSuccess = (response.get("success").toString() == "true")
//            if(isSuccess)
//                layoutFinish.animate().translationX(0F).duration=400
//            else{
//                Toast.makeText(applicationContext,"Network Error",Toast.LENGTH_LONG).show()
//            }
        }

        cardOk.setOnClickListener{
            startActivity(Intent(this@MyCartActivity,HomeActivity::class.java))
        }

        backIcon.setOnClickListener{
            super.onBackPressed()
        }
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
