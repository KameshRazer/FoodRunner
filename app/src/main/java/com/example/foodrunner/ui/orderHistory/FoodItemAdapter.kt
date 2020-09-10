package com.example.foodrunner.ui.orderHistory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrunner.R

class FoodItemAdapter(private val foodList: ArrayList<ArrayList<String>>): RecyclerView.Adapter<FoodItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_display_order_history,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(foodList[position])
    }

    class ViewHolder(itemView :View):RecyclerView.ViewHolder(itemView){

        val resName = itemView.findViewById<TextView>(R.id.rvdo_res_name)
        val totCost = itemView.findViewById<TextView>(R.id.rvdo_total_cost)
        fun bindItems(data :ArrayList<String>){
            resName.text = data[0]
            totCost.text = data[1]
        }

    }
}