package com.example.foodrunner.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrunner.HomeActivity
import com.example.foodrunner.R
import com.example.foodrunner.SelectFood
import com.example.foodrunner.ui.DatabaseHandler
import com.squareup.picasso.Picasso

class HomeDataAdapter(val foodList: ArrayList<ArrayList<String>>): RecyclerView.Adapter<HomeDataAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_display_hotel,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(foodList[position],position)
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bindItems(list:ArrayList<String>, position:Int){
            val name = itemView.findViewById<TextView>(R.id.rvdh_food_name)
            val image = itemView.findViewById<ImageView>(R.id.rvdh_image_food)
            val rating = itemView.findViewById<TextView>(R.id.rvdh_rating)
            val cost = itemView.findViewById<TextView>(R.id.rvdh_food_rate)
            val fav = itemView.findViewById<ToggleButton>(R.id.rvdh_icon_heart)
            val layout = itemView.findViewById<RelativeLayout>(R.id.rvdh_relativelayout)
            val context = itemView.context

            name.text=list[0]
            rating.text=list[2]
            cost.text = "${list[3]}/Person"
            Picasso.get().load(list[1]).into(image)

            fav.setOnClickListener(View.OnClickListener {
                val databaseHandler:DatabaseHandler = DatabaseHandler(itemView.context)
                if (fav.isChecked)
                    databaseHandler.insertData(FavouriteRestaurants(list[4].toInt(),list[0],list[1],list[2],list[3]))
                else
                    databaseHandler.deleteData(FavouriteRestaurants(list[4].toInt(),list[0],list[1],list[2],list[3]))
            })

            layout.setOnClickListener(View.OnClickListener {
                val selectFood = Intent(context,SelectFood::class.java)
                selectFood.putExtra("hotelName",list[0])
                selectFood.putExtra("resId",list[4])
                context.startActivity(selectFood)
            })

        }

    }
}