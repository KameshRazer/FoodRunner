package com.example.foodrunner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import okhttp3.*
import java.io.IOException
import java.lang.Exception
import java.util.concurrent.CountDownLatch

class TempActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temp)

        val client = OkHttpClient()
        val countDownLatch = CountDownLatch(1)
        val headers = Headers.of(getHeader())
        var finalResponse = "Error"
        try {
            val request = okhttp3.Request.Builder()
                .url("http://13.235.250.119/v2/restaurants/fetch_result/1")
                .headers(headers)
                .build()
            client.newCall(request).enqueue(object :Callback{
                override fun onFailure(call: Call, e: IOException) {
                    println("Failure")
                    countDownLatch.countDown()
                }

                override fun onResponse(call: Call, response: Response) {
                    finalResponse = response.body()!!.string()
                    countDownLatch.countDown()
                }
            })
        }
        catch (e:Exception){
            println("Catch : ${e.message}")
        }
        countDownLatch.await()
        println("Response : $finalResponse")

    }
    private fun getHeader():MutableMap<String,String>{
        val header = HashMap<String,String>()
        header["content-type"] = "application/json"
        header["token"] = "9bf534118365f1"
        return header
    }
}
