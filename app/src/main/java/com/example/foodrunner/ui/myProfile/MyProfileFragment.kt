package com.example.foodrunner.ui.myProfile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.foodrunner.R
class MyProfileFragment : Fragment(){

    private lateinit var logInfo:SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        slideshowViewModel=
//            ViewModelProviders.of(this).get(SlideshowViewModel::class.java)
        setHasOptionsMenu(true)
        val root = inflater.inflate(R.layout.fragment_myprofile, container, false)
        logInfo = activity?.getSharedPreferences("LoginInfo",Context.MODE_PRIVATE)!!
        val name = root.findViewById<EditText>(R.id.myProfile_name)
        val mobile = root.findViewById<EditText>(R.id.myProfile_mobileNo)
        val emailAdd = root.findViewById<EditText>(R.id.myProfile_emailAdd)
        val deliveryAdd = root.findViewById<EditText>(R.id.myProfile_deliveryAdd)
        name.setText(logInfo.getString("name","Error"))
        mobile.setText(logInfo.getString("mobileNo","Error"))
        emailAdd.setText(logInfo.getString("emailAdd","Error"))
        deliveryAdd.setText(logInfo.getString("deliveryAdd","Error"))
        return root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.clear()
    }
}