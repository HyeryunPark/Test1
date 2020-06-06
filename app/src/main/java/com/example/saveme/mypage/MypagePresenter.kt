package com.example.saveme.mypage

import android.content.Context
import android.util.Log
import com.example.saveme.model.userData
import com.example.saveme.network.RetrofitClient
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MypagePresenter : MypageContract.Presenter {

    private var mypageView: MypageContract.View? = null

    // LOGOUT
    override fun logout(context: Context) {
        val client: OkHttpClient = RetrofitClient.getClient(context, "addCookie")
        val retrofitInterface = RetrofitClient.retrofitInterface(client)

        retrofitInterface.logout().enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.e("Logout Success", Gson().toJson(response.body()))

                if (response.isSuccessful) {
                    Log.e("logout", "성공")
                    mypageView!!.logout()
                }

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("Logout Fail", t.toString())
            }

        })

    }

    override fun getUser(context: Context) {
        val client: OkHttpClient = RetrofitClient.getClient(context, "addCookie")
        val retrofitInterface = RetrofitClient.retrofitInterface(client)

        retrofitInterface.getUser().enqueue(object : Callback<userData> {

            override fun onResponse(call: Call<userData>, response: Response<userData>) {
                if (response.isSuccessful) {
                    Log.e("Success", Gson().toJson(response.body()))

                    mypageView?.setUserData(response.body()!!.username, response.body()!!.email)
                } else {

                }
            }

            override fun onFailure(call: Call<userData>, t: Throwable) {
                Log.e("getUser", t.toString())
            }
        })
    }

    override fun takeView(view: MypageContract.View) {
        mypageView = view
    }

    override fun dropView() {
        mypageView = null
    }
}