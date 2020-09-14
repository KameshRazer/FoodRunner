package com.example.foodrunner

import android.content.SharedPreferences
import io.ktor.client.request.forms.FormBuilder
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.CountDownLatch
import kotlin.reflect.typeOf

class MyMessenger {
//    Sending post request to server
    fun sendPOSTRequest(url :String , reqBody :RequestBody):JSONObject{
        val client = OkHttpClient()
        val countDownLatch = CountDownLatch(1)
        val headers = Headers.of(getHeader())
        var finalResponse ="Error"

        try{
            val request = Request.Builder()
                .url(url)
                .headers(headers)
                .post(reqBody)
                .build()
//            println("Messenger : ${request}")
            client.newCall(request).enqueue(object :Callback{
                override fun onFailure(call: Call, e: IOException) {
                    println("Failure")
                    countDownLatch.countDown()
                }

                override fun onResponse(call: Call, response: okhttp3.Response) {
                    finalResponse = response.body()!!.string()
                    countDownLatch.countDown()
                }
            } )
        }
        catch (e:Exception)
        {
            println("Catch : ${e.message}")
        }
        countDownLatch.await()
        return JSONObject(finalResponse)
    }
//  Sending GET Request to server

    fun sendGETRequest(url :String) : JSONObject{

        val client = OkHttpClient()
        var result ="Error"
        val countDownLatch = CountDownLatch(1)
        val headers = Headers.of(getHeader())

        try{
            val request = okhttp3.Request.Builder().url(url)
                .headers(headers)
                .build()
            client.newCall(request).enqueue(object :Callback{
                override fun onFailure(call: Call, e: IOException) {
                    println(e.message.toString())
                    countDownLatch.countDown()
                }

                override fun onResponse(call: Call, response: Response) {
                    result = response.body()!!.string()
                    countDownLatch.countDown()
                }
            })
        }catch (e :java.lang.Exception){
            println(e.message.toString())
            countDownLatch.countDown()
        }
        countDownLatch.await()
        return JSONObject(result)
    }

    private fun getHeader():MutableMap<String,String>{
        val header = HashMap<String,String>()
        header["content-type"] = "application/json"
        header["token"] = "9bf534118365f1"
        return header
    }

     fun saveDataInLocal(logInfo:SharedPreferences,result:JSONObject){
        logInfo.edit().putBoolean("logStatus", true).apply()
        logInfo.edit().putString("userId",result.getString("user_id")).apply()
        logInfo.edit().putString("name",result.getString("name")).apply()
        logInfo.edit().putString("emailAdd",result.getString("email")).apply()
        logInfo.edit().putString("deliveryAdd",result.getString("address")).apply()
        logInfo.edit().putString("mobileNo", result.getString("mobile_number")).apply()
    }
}