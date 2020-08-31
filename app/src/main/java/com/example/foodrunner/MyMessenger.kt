package com.example.foodrunner

import io.ktor.client.request.forms.FormBuilder
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.CountDownLatch
import kotlin.reflect.typeOf

class MyMessenger {
    fun sendRequest(url :String , reqBody :RequestBody):String{
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
        return finalResponse
    }

    private fun getHeader():MutableMap<String,String>{
        val header = HashMap<String,String>()
        header["content-type"] = "application/json"
        header["token"] = "9bf534118365f1"
        return header
    }
}