package com.eterocell.samples.network

import android.util.Log
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okio.IOException

object OkHttpTestClient {
    private val client = OkHttpClient()

    fun sendPostRequest() {
        val url = "https://httpbin.org/post" // 可替换为任意可用 POST 接口
        val jsonMediaType = "application/json; charset=utf-8".toMediaType()

        val jsonBody =
            """
            {
                "username": "tester",
                "password": "123456"
            }
            """.trimIndent()

        val request =
            Request
                .Builder()
                .url(url)
                .addHeader("Authorization", "Bearer demo_token_123")
                .addHeader("X-Custom-Header", "MyHeaderValue")
                .post(jsonBody.toRequestBody(jsonMediaType))
                .build()

        client.newCall(request).enqueue(
            object : Callback {
                override fun onFailure(
                    call: Call,
                    e: IOException,
                ) {
                    Log.e("OkHttpTest", "Request failed: ${e.message}")
                }

                override fun onResponse(
                    call: Call,
                    response: Response,
                ) {
                    Log.d("OkHttpTest", "Response: ${response.body.string()}")
                }
            },
        )
    }
}
