package com.example.foodrunner

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.util.ArrayList

class DisplayResultActivity : AppCompatActivity() {


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_result)

        val displaytopic = findViewById<TextView>(R.id.displayTopic)
        val displayText = findViewById<TextView>(R.id.displayText)
        val btnLogout = findViewById<Button>(R.id.dr_btnLogout)
        val logInfo = getSharedPreferences("LoginInfo", Context.MODE_PRIVATE)

        val result =  intent.getStringArrayListExtra("details")
        val key = intent.getStringArrayListExtra("key")
        val login = logInfo.getBoolean("logStatus",false)
        var count =0
        displaytopic.text = intent.getStringExtra("topic")
        for(i in result){
            displayText.text = displayText.text.toString()+key[count++]+i+"\n\n"
        }
//        displayText.setText("lksdjf")
//        println(result.size)
        btnLogout.setOnClickListener{
            logInfo.edit().clear().apply()
            startActivity(Intent(this@DisplayResultActivity,LoginActivity::class.java))
        }
        if (!login)
            btnLogout.visibility=View.GONE
    }

    override fun onRestart() {
        super.onRestart()
        startActivity(Intent(this@DisplayResultActivity,LoginActivity::class.java))
    }
}
