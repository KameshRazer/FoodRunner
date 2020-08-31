package com.example.foodrunner.ui.home
//
//import com.example.foodrunner.R
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import java.util.*
//
//
//private class MarkAdapter internal constructor(private val dataList: List<ArrayList<String>>) :
//    RecyclerView.Adapter<MarkAdapter.ViewHolder>() {
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): ViewHolder {
//        val context = parent.context
//        val layoutInflater = LayoutInflater.from(context)
//        val DataListView: View =
//            layoutInflater.inflate(R.layout.recyclerview_display_food, parent, false)
//        val viewHolder: ViewHolder
//        viewHolder = ViewHolder(DataListView)
//        return viewHolder
//    }
//
//    override fun onBindViewHolder(
//        viewHolder: ViewHolder,
//        position: Int
//    ) {
//    }
//
//    override fun getItemCount(): Int {
//        return dataList.size
//    }
//
//    internal class ViewHolder(itemView: View) :
//        RecyclerView.ViewHolder(itemView){
//
//    }
//
//
//}
//class CustomAdapter(val userList: ArrayList<User>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
//
//    //this method is returning the view for each item in the list
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter.ViewHolder {
//        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_layout, parent, false)
//        return ViewHolder(v)
//    }
//
//    //this method is binding the data on the list
//    override fun onBindViewHolder(holder: CustomAdapter.ViewHolder, position: Int) {
//        holder.bindItems(userList[position])
//    }
//
//    //this method is giving the size of the list
//    override fun getItemCount(): Int {
//        return userList.size
//    }
//
//    //the class is hodling the list view
//    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//        fun bindItems(user: User) {
//            val textViewName = itemView.findViewById(R.id.textViewUsername) as TextView
//            val textViewAddress  = itemView.findViewById(R.id.textViewAddress) as TextView
//            textViewName.text = user.name
//            textViewAddress.text = user.address
//        }
//    }
//}