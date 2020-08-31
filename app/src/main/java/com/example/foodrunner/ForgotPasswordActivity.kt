package com.example.foodrunner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText

class ForgotPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        supportActionBar?.hide()
        val emailAdd = findViewById<EditText>(R.id.fp_emailAdd)
        val mobileNo= findViewById<EditText>(R.id.fp_mobileNo)
        val btnNext = findViewById<Button>(R.id.fp_btnForgot)

        val key : ArrayList<String> = ArrayList(2)
        key.add("Mobile Number : ")
        key.add("Email Address : ")
        btnNext.setOnClickListener{
            if(TextUtils.isEmpty(emailAdd.text))
                emailAdd.setError("Enter Email Address")
            else if(TextUtils.isEmpty(mobileNo.text))
                mobileNo.setError("Enter Mobile Number")
            else {
                val value: ArrayList<String> = ArrayList(2)
                val topic = "Forgot Password"
                value.add(mobileNo.text.toString())
                value.add(emailAdd.text.toString())
                mobileNo.setText("")
                emailAdd.setText("")
                val display = Intent(this@ForgotPasswordActivity, DisplayResultActivity::class.java)
                display.putStringArrayListExtra("details", value)
                display.putStringArrayListExtra("key", key)
                display.putExtra("topic", topic)
                startActivity(display)
            }
        }
    }
}
