package com.example.saveme.network

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class ReceivedCookiesInterceptor : Interceptor {    // 로그인 후 받은 Response 에서 토큰을 가져와 Preference 에 저장
    private lateinit var preferences: SharedPreferences
    internal var context: Context

    //생성자
    constructor(context: Context) {
        this.context = context
    }

    override fun intercept(chain: Interceptor.Chain): Response {

        val originalResponse = chain.proceed(chain.request())
        val body = originalResponse.body()?.string()

        if (body?.contains("token")!!) {
            val token = body.split("\"".toRegex())
            val token2 = token[15].split(":")
            Log.e("token", token2[0])

            preferences = context.getSharedPreferences("USERSIGN", 0)
            val editor = preferences.edit()
            editor.putString("Cookie", token2[0])
            editor.commit()
        }

        return originalResponse
    }
}