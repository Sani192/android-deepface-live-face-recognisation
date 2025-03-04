package com.trident.utils

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

object NetworkUtils {

    private val client = OkHttpClient()

    /*fun callPostEndPoint(data: JSONObject, callback: (Boolean, String?) -> Unit) {
        val url = "127.0.0.1:5000/live-face-recognition/start"
        val mediaType = "application/json;charset=utf-8".toMediaTypeOrNull()
        val body = data.toString().toRequestBody(mediaType)
        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                callback(false, e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseData = response.body?.string()
                    callback(true, responseData)
                } else {
                    callback(false, response.message)
                }
            }
        })
    }*/

    fun callGetEndPoint(isStart: Boolean, callback: (Boolean, String?) -> Unit) {
        val startUrl = "http://192.168.29.170:5000/live-face-recognition/start"
        val stopUrl = "http://192.168.29.170:5000/live-face-recognition/stop"
        val url = if(isStart) startUrl else stopUrl
        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                callback(false, e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseData = response.body?.string()
                    callback(true, responseData)
                } else {
                    callback(false, response.message)
                }
            }
        })
    }
}