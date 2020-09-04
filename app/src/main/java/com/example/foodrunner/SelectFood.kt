package com.example.foodrunner

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONObject
import org.w3c.dom.Text
import java.lang.Exception

interface OnItemClickListerner{
    fun onItemClick(isTrue :Boolean)
}

class SelectFood : AppCompatActivity(),OnItemClickListerner {
    private lateinit var confirm : TextView
    private  var count :Int  =0
    private var isUp :Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_food)

        val backArrow = findViewById<ImageView>(R.id.sf_icon_back)
        val recyclerView = findViewById<RecyclerView>(R.id.sf_recyclerview)
        val hotelName = findViewById<TextView>(R.id.sf_hotel_name)

//        CONFIRM ORDER animation setup
        confirm = findViewById(R.id.sf_confirm_order)
        confirm.visibility = View.GONE

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
        val adapter = SelectFoodAdapter(foodList,applicationContext,this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
//        MyAnimationDown()
        backArrow.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@SelectFood,HomeActivity::class.java))
        })
        hotelName.setOnClickListener(View.OnClickListener {

        })


    }

     private fun MyAnimationUp(){
         val animationUp =TranslateAnimation(0F, 0F,  (confirm.height).toFloat(),0F)
         animationUp.duration = 300
         animationUp.fillAfter = true
         isUp = !isUp
         confirm.visibility=View.VISIBLE
         confirm.startAnimation(animationUp)
//         println("Animation Up")
    }
    private fun MyAnimationDown(){
        val animationDown =TranslateAnimation(0F, 0F, 0F, (confirm.height).toFloat())
        animationDown.duration =300
        animationDown.fillAfter = true
        isUp = !isUp
        confirm.startAnimation(animationDown)
        confirm.visibility = View.GONE
//        println("Animation Down")
    }

//  Adapter of Food selection recycler view
    class SelectFoodAdapter(private val foodList: ArrayList<ArrayList<String>>,val context: Context,private var onItemClickListerner: OnItemClickListerner):RecyclerView.Adapter<SelectFoodAdapter.ViewHolder>(){
        var count =0
        val activity = SelectFood()
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
            holder.button.setOnClickListener(View.OnClickListener {
               onItemClickListerner.onItemClick(holder.button.isChecked)
            })
        }
        class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
            val foodName = itemView.findViewById<TextView>(R.id.rvdf_food_name)
            val s_no = itemView.findViewById<TextView>(R.id.rvdf_s_no)
            val cost = itemView.findViewById<TextView>(R.id.rvdf_cost)
            val button = itemView.findViewById<ToggleButton>(R.id.rvdf_button)
            val confirm = itemView.findViewById<TextView>(R.id.sf_confirm_order)

            @SuppressLint("SetTextI18n", "ResourceAsColor")
            fun bindItems(data:ArrayList<String>, position:Int){
                s_no.text = (position+1).toString()
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
//        if(!confirm.isVisible)
//            confirm.visibility=View.VISIBLE
        if(count > 0 && isUp)
            MyAnimationUp()
        if(count == 0 && !isUp)
            MyAnimationDown()

    }

}
