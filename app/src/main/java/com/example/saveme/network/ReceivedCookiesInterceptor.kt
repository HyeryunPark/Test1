package com.example.saveme.network

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.util.HashSet

class ReceivedCookiesInterceptor : Interceptor {
    private lateinit var preferences: SharedPreferences
    internal var context: Context

    //생성자
    constructor(context: Context) {
        this.context = context
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            val cookies = HashSet<String>()
            for (header in originalResponse.headers("Set-Cookie")) {
                cookies.add(header)
            }

            // Preference에 cookies를 넣어주는 작업을 수행
            val arr = cookies.toString().split(";".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
            val arr2 = arr[0].split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            Log.e("cookieInterceptor", arr2[1])
            preferences = context.getSharedPreferences("USERSIGN", 0)
            val editor = preferences.edit()
            editor.putString("Cookie", arr2[1])
            editor.commit()
        }
        return originalResponse
    }
}