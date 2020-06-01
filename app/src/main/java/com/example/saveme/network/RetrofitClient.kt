package com.example.saveme.network

import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private const val BASE_URL = "http://10.0.2.2:8000"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    fun getClient(context: Context, type: String): OkHttpClient {
        when (type) {
            "addCookie" -> {    // 쿠키를 추가 할때
                val addCookiesInterceptor = AddCookiesInterceptor(context)
                val client = OkHttpClient.Builder()
                    .addInterceptor(addCookiesInterceptor)
                    .addInterceptor(loggingInterceptor)
                    .build()
                return client
            }
            "receiveCookie" -> {    // 쿠키를 저장할 때
                val receivedCookiesInterceptor = ReceivedCookiesInterceptor(context)
                val client = OkHttpClient.Builder()
                    .addInterceptor(receivedCookiesInterceptor)
                    .addInterceptor(loggingInterceptor)
                    .build()
                return client
            }
            else -> {   // 토큰이 없는 경우 (회원가입)
                val client = OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .build()
                return client
            }
        }
    }

    // retrofit
    private fun retrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    fun retrofitInterface(client: OkHttpClient): RetrofitInterface =
        retrofit(client).create(RetrofitInterface::class.java)

/*    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofitInterface = retrofit.create(RetrofitInterface::class.java)
    }*/


}