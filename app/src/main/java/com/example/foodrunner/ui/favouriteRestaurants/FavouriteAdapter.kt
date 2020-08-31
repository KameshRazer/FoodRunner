package com.example.foodrunner.ui.favouriteRestaurants

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrunner.R
import com.example.foodrunner.ui.DatabaseHandler
import com.example.foodrunner.ui.home.FavouriteRestaurants
import com.squareup.picasso.Picasso

class FavoriteAdapter (val foodList: MutableList<FavouriteRestaurants>): RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_display_food,parent,false)
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
            val name = itemView.findViewById<TextView>(R.id.rvdf_food_name)
            val image = itemView.findViewById<ImageView>(R.id.rvdf_image_food)
            val rating = itemView.findViewById<TextView>(R.id.rvdf_rating)
            val cost = itemView.findViewById<TextView>(R.id.rvdf_food_rate)
            val fav = itemView.findViewById<ToggleButton>(R.id.rvdf_icon_heart)
            fav.isChecked=true
            fav.setOnClickListener(View.OnClickListener {
                val databaseHandler: DatabaseHandler = DatabaseHandler(itemView.context)
                val result = databaseHandler.deleteData(list)
            })

            name.text=list.name
            rating.text=list.rating
            cost.text = list.cost+"/Person"
            Picasso.get().load(list.image).into(image)
        }

    }
}