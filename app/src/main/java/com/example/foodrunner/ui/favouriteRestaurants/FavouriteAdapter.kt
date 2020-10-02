package com.example.foodrunner.ui.favouriteRestaurants

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrunner.R
import com.example.foodrunner.SelectFood
import com.example.foodrunner.ui.DatabaseHandler
import com.example.foodrunner.ui.home.FavouriteRestaurants
import com.squareup.picasso.Picasso

class FavoriteAdapter (val foodList: MutableList<FavouriteRestaurants>): RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

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

        fun bindItems(list:FavouriteRestaurants,position:Int){
            val name = itemView.findViewById<TextView>(R.id.rvdh_food_name)
            val image = itemView.findViewById<ImageView>(R.id.rvdh_image_food)
            val rating = itemView.findViewById<TextView>(R.id.rvdh_rating)
            val cost = itemView.findViewById<TextView>(R.id.rvdh_food_rate)
            val fav = itemView.findViewById<ToggleButton>(R.id.rvdh_icon_heart)
            val layout = itemView.findViewById<RelativeLayout>(R.id.rvdh_relativelayout)
            val context = itemView.context
            fav.isChecked=true
            fav.setOnClickListener(View.OnClickListener {
                val databaseHandler: DatabaseHandler = DatabaseHandler(itemView.context)
                if(fav.isChecked)
                    databaseHandler.insertData(list)
                else
                    databaseHandler.deleteData(list)
            })

            layout.setOnClickListener {
                val selectFood = Intent(context,SelectFood::class.java)
                selectFood.putExtra("hotelName",list.name)
                selectFood.putExtra("resId",list.id.toString())
                context.startActivity(selectFood)
            }

            name.text=list.name
            rating.text=list.rating
            cost.text = list.cost+"/Person"
            Picasso.get().load(list.image).into(image)
        }

    }
}