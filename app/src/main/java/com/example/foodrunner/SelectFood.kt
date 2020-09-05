package com.example.foodrunner

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONObject

interface OnItemClickListener{
    fun onItemClick(isTrue :Boolean)
}

class SelectFood : AppCompatActivity(),OnItemClickListener {
    private lateinit var confirm : TextView
    private  var count :Int  =0
    private var isUp :Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_food)

        val backArrow = findViewById<ImageView>(R.id.sf_icon_back)
        val recyclerView = findViewById<RecyclerView>(R.id.sf_recyclerview)
        val hotelName = findViewById<TextView>(R.id.sf_hotel_name)
        confirm = findViewById(R.id.sf_confirm_order)

        val foodList =ArrayList<ArrayList<String>>(1)
        var url = "http://13.235.250.119/v2/restaurants/fetch_result/"

        hotelName.text = intent.getStringExtra("hotelName")
        url += intent.getStringExtra("id")

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
        val adapter = SelectFoodAdapter(foodList,this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        backArrow.setOnClickListener {
            startActivity(Intent(this@SelectFood,HomeActivity::class.java))
        }
    }

//  Adapter of Food selection recycler view
    class SelectFoodAdapter(private val foodList: ArrayList<ArrayList<String>>,private var onItemClickListener: OnItemClickListener):RecyclerView.Adapter<SelectFoodAdapter.ViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_display_food,parent,false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return foodList.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bindItems(foodList[position],position)
//            holder.itemView.setOnClickListener(this)
            holder.button.setOnClickListener {
                onItemClickListener.onItemClick(holder.button.isChecked)
            }
        }
        class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
            private val foodName: TextView = itemView.findViewById(R.id.rvdf_food_name)
            private val sNo: TextView = itemView.findViewById(R.id.rvdf_s_no)
            private val cost: TextView = itemView.findViewById(R.id.rvdf_cost)
            val button: ToggleButton = itemView.findViewById(R.id.rvdf_button)

            @SuppressLint("SetTextI18n", "ResourceAsColor")
            fun bindItems(data:ArrayList<String>, position:Int){
                sNo.text = (position+1).toString()
                foodName.text = data[1]
                cost.text = "Rs. "+data[2]
            }

        }

    }

    override fun onItemClick(isTrue: Boolean) {
        if(isTrue){
            count--
        }else{
            count++
        }

        if(count > 0 && isUp){
            confirm.animate().translationY(0F).duration=400
            isUp = !isUp
        }

        if(count == 0 && !isUp) {
            confirm.animate().translationY(150F).duration = 400
            isUp =!isUp
        }


    }

}
