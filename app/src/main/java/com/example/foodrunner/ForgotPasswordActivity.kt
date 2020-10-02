package com.example.foodrunner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject

class ForgotPasswordActivity : AppCompatActivity() {

//    private val TAG ="ForgotPasswordActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        val urlForgot = "http://13.235.250.119/v2/forgot_password/fetch_result"
        val urlReset = "http://13.235.250.119/v2/reset_password/fetch_result"

        val emailAdd = findViewById<EditText>(R.id.fp_emailAdd)
        val mobileNo= findViewById<EditText>(R.id.fp_mobileNo)
        val btnNext = findViewById<Button>(R.id.fp_btnForgot)

        val layoutOTP = findViewById<RelativeLayout>(R.id.fp_layout_otp)
        val otp = findViewById<EditText>(R.id.fp_otp)
        val newPass = findViewById<EditText>(R.id.fp_new_password)
        val confirmPass = findViewById<EditText>(R.id.fp_confirm_password)
        val btnSubmit = findViewById<Button>(R.id.fp_btnSubmit)

        val key : ArrayList<String> = ArrayList(2)
        key.add("Mobile Number : ")
        key.add("Email Address : ")
        btnNext.setOnClickListener{
            if(TextUtils.isEmpty(emailAdd.text))
                emailAdd.setError("Enter Email Address")
            else if(TextUtils.isEmpty(mobileNo.text))
                mobileNo.setError("Enter Mobile Number")
            else {
                val actualData = JSONObject()
                val json = MediaType.parse("application/json;charset*utf-8")
                actualData.put("mobile_number",mobileNo.text)
                actualData.put("email",emailAdd.text)

                val reqBody = RequestBody.create(json,actualData.toString())
                var response = MyMessenger().sendPOSTRequest(urlForgot,reqBody)

                response = JSONObject(response.get("data").toString())
                val isSuccess = (response.get("success").toString() == "true")

                if(isSuccess){
                    val isFirstTry = (response.getString("first_try").toString() == "false")
                    btnNext.visibility = View.INVISIBLE
                    layoutOTP.animate().translationX(0F).duration = 400
                    if(isFirstTry){
                        Toast.makeText(applicationContext,"Please try after 24 hrs",Toast.LENGTH_LONG).show()
                    }
                }else{
                    val msg = response.getString("errorMessage")
                    Toast.makeText(applicationContext,msg,Toast.LENGTH_LONG).show()
                }
            }
        }

        btnSubmit.setOnClickListener {
            if (TextUtils.isEmpty(otp.text))
                otp.error ="Enter OTP"
            else if(TextUtils.isEmpty(newPass.text))
                newPass.error = "Enter New Password"
            else if(TextUtils.isEmpty(confirmPass.text))
                confirmPass.error = "Enter New Password"
            else if(newPass.text.toString() != confirmPass.text.toString()){
                println("${newPass.text}  -> ${confirmPass.text}")
                confirmPass.error = "Password Mismatch"
                confirmPass.setText("")
            }else{
                val actualData = JSONObject()
                val json = MediaType.parse("application/json;charset*utf-8")
                actualData.put("mobile_number",mobileNo.text)
                actualData.put("password",newPass.text)
                actualData.put("otp",otp.text)

                val reqBody = RequestBody.create(json,actualData.toString())
                var response = MyMessenger().sendPOSTRequest(urlReset,reqBody)
                response = JSONObject(response.get("data").toString())

                val isSuccess = (response.get("success").toString() == "true")
                if(isSuccess){
                    val msg = response.getString("successMessage")
                    Toast.makeText(applicationContext,msg,Toast.LENGTH_LONG).show()
                    startActivity(Intent(this@ForgotPasswordActivity, LoginActivity::class.java))
                    finish()
                }else{
                    otp.error = "Incorrect OTP"
                    Toast.makeText(applicationContext,"Invalid OTP",Toast.LENGTH_LONG).show()
                }
            }
        }

    }
}
