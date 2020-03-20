package com.example.saveme.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    val retrofitInterface: RetrofitInterface
    const val BASE_URL = "https://cc7ad498.ngrok.io"    // ngrok으로 포워딩한 주소

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofitInterface = retrofit.create(RetrofitInterface::class.java)
    }

}