package com.example.foodrunner

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {

    private lateinit var url : String
    private lateinit var logInfo:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
//        supportActionBar?.hide()
        url ="http://13.235.250.119/v2/register/fetch_result"



        logInfo = getSharedPreferences("LoginInfo", Context.MODE_PRIVATE)
        val name = findViewById<EditText>(R.id.reg_name)
        val emailAdd = findViewById<EditText>(R.id.reg_emailAdd)
        val mobileNo = findViewById<EditText>(R.id.reg_mobileNo)
        val deliveryAdd = findViewById<EditText>(R.id.reg_deliveryAdd)
        val password = findViewById<EditText>(R.id.reg_password)
        val confirmPass = findViewById<EditText>(R.id.reg_confirmPass)
        val btnRegister =  findViewById<Button>(R.id.reg_btnRegister)

        name.setText("Razer")
        emailAdd.setText("razer@nfs.com")
        mobileNo.setText("9993886666")
        password.setText("123456")
        deliveryAdd.setText("NFS")
        confirmPass.setText("123456")

        btnRegister.setOnClickListener{
            if(TextUtils.isEmpty(name.text))
                name.setError("Enter your Name")
            else if(TextUtils.isEmpty(emailAdd.text))
                emailAdd.setError("Enter your Email Address")
            else if(TextUtils.isEmpty(mobileNo.text))
                mobileNo.setError("Enter your Mobile Number")
            else if(mobileNo.length()!=10)
                mobileNo.error = "Invalid Number"
            else if(TextUtils.isEmpty(deliveryAdd.text))
                deliveryAdd.setError("Enter your Delivery Address ")
            else if(TextUtils.isEmpty(password.text))
                password.setError("Enter your password")
            else if ((confirmPass.text.toString()).equals(password.text.toString())) {

                val actualData = JSONObject()
                val json = MediaType.parse("application/json;charset*utf-8")
                actualData.put("name",name.text)
                actualData.put("mobile_number",mobileNo.text)
                actualData.put("password",password.text)
                actualData.put("address",deliveryAdd.text)
                actualData.put("email",emailAdd.text)

                val reqBody = RequestBody.create(json,actualData.toString())
                var response = JSONObject(MyMessenger().sendPOSTRequest(url,reqBody))
                response = JSONObject(response.getString("data"))
                val isSuccess =(response.getString("success") == "true")

                if (isSuccess){
                    MyMessenger().saveDataInLocal(logInfo, JSONObject(response.getString("data")))
                }else{
                    mobileNo.error = "Error"
                    emailAdd.error = "Error"
                    Toast.makeText(applicationContext,"Mobile number of Email Id is already registered",Toast.LENGTH_LONG).show()
                }

                val display = Intent(this@RegisterActivity, LoginActivity::class.java)

//                name.setText("")
//                emailAdd.setText("")
//                mobileNo.setText("")
//                deliveryAdd.setText("")
//                password.setText("")
//                confirmPass.setText("")
                Toast.makeText(this,"Register Success",Toast.LENGTH_LONG).show()
//                startActivity(display)
            }else{
                confirmPass.setText("")
                confirmPass.setError("Password Mismatch")
            }
        }
    }
}
