package com.example.foodrunner

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    private lateinit var  url : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

//        supportActionBar?.hide()
        val textRegister = findViewById<TextView>(R.id.log_register)
        val textForgot = findViewById<TextView>(R.id.log_forgotPassword)
        val btnLogin = findViewById<Button>(R.id.log_btnLogin)
        val mobileNo = findViewById<EditText>(R.id.log_mobileNo)
        val password = findViewById<EditText>(R.id.log_password)
        val logInfo = getSharedPreferences("LoginInfo", Context.MODE_PRIVATE)
        url = "http://13.235.250.119/v2/login/fetch_result"
        //Previous Login Check
        val logStatus = logInfo.getBoolean("logStatus",false)
        if(logStatus){
            val display = Intent(this@LoginActivity,HomeActivity::class.java)
            startActivity(display)
        }

        textRegister.setOnClickListener{
            startActivity(Intent(this@LoginActivity,RegisterActivity::class.java))
        }
        textForgot.setOnClickListener{
            startActivity(Intent(this@LoginActivity,ForgotPasswordActivity::class.java))
        }
        btnLogin.setOnClickListener {
            if(TextUtils.isEmpty(mobileNo.text))
                mobileNo.error = "Enter Mobile Number"
            else if(TextUtils.isEmpty(password.text))
                password.error = "Enter Password"
            else {
                val actualData = JSONObject()
                val json = MediaType.parse("application/json;charset*utf-8")
                actualData.put("mobile_number",mobileNo.text)
                actualData.put("password",password.text)

                val reqBody = RequestBody.create(json,actualData.toString())
                var response = MyMessenger().sendPOSTRequest(url,reqBody)

                response = JSONObject(response.get("data").toString())
                val isSuccess = (response.get("success").toString() == "true")

                if(isSuccess){
                    MyMessenger().saveDataInLocal(logInfo,JSONObject(response.get("data").toString()))
                    startActivity(Intent(this@LoginActivity,HomeActivity::class.java))
                    this.finish()
                }else{
                    val isError = (response.getString("errorMessage") == "Incorrect password")
                    if(isError){
                        password.setText("")
                        password.error = "Incorrect password"
//                        Toast.makeText(applicationContext,"Incorrect Password",Toast.LENGTH_LONG).show()
                    }else{
                        mobileNo.setText("")
                        password.setText("")
                        mobileNo.error = "Invalid Mobile Number"
//                        Toast.makeText(applicationContext,"Invalid Mobile Number",Toast.LENGTH_LONG).show()
                    }
                }

            }
        }


    }

    override fun onBackPressed() {
        finishAffinity()
    }

}
