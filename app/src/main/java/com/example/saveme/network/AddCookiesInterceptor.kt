package com.example.saveme.network

import android.content.Context
import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Response

class AddCookiesInterceptor : Interceptor {     // Request마타 Preference에 저장되어있는 토큰을 함께 Header에 넣어줌
    private lateinit var preferences: SharedPreferences
    internal var context: Context

    //생성자
    constructor(context: Context) {
        this.context = context
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        preferences = context.getSharedPreferences("USERSIGN", 0)
        val builder = chain.request().newBuilder()

        // Preference에서 cookies를 가져오는 작업을 수행
        builder.addHeader("Cookie", "connect.sid=" + preferences.getString("Cookie", "")!!)
        builder.addHeader("Content-Type", "application/json")

        // Web,Android,iOS 구분을 위해 User-Agent세팅
        builder.removeHeader("User-Agent").addHeader("User-Agent", "Android")

        return chain.proceed(builder.build())
    }


}