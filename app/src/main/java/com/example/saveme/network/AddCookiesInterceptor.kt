package com.example.saveme.network

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class AddCookiesInterceptor : Interceptor {     // Request 마다 Preference 에 저장되어있는 토큰을 함께 Header 에 넣어줌
    private lateinit var preferences: SharedPreferences
    internal var context: Context

    //생성자
    constructor(context: Context) {
        this.context = context
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        preferences = context.getSharedPreferences("USERSIGN", 0)
        val builder = chain.request().newBuilder()

        // preference 에서 token을 가져와 Header에 추가
        builder.addHeader("Authorization", "Token " + preferences.getString("Cookie", ""))

        // Web,Android,iOS 구분을 위해 User-Agent세팅
        builder.removeHeader("User-Agent").addHeader("User-Agent", "Android")

        return chain.proceed(builder.build())
    }


}