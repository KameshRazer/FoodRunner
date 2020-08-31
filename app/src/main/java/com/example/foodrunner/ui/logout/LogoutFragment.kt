package com.example.foodrunner.ui.logout

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.Fragment
import com.example.foodrunner.HomeActivity
import com.example.foodrunner.LoginActivity
import com.example.foodrunner.R

class LogoutFragment : Fragment() {
    private lateinit var logInfo:SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_logout, container, false)
        logInfo = activity?.getSharedPreferences("LoginInfo", Context.MODE_PRIVATE)!!
        logInfo.edit().clear().apply()
        logout()
        return view
    }

    override fun onResume() {
        super.onResume()
//        println("On resume")
        if(logInfo.getBoolean("logStatus",false))
            logout()
    }
    private fun logout(){
        startActivity(Intent(this@LogoutFragment.context, LoginActivity::class.java))
    }

}
