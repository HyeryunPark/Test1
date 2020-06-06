package com.example.saveme.login

import android.content.Context
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.example.saveme.model.GetUser
import com.example.saveme.model.userData
import com.example.saveme.network.RetrofitClient
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPresenter : LoginContract.Presenter {

    private var loginView: LoginContract.View? = null

    override fun takeView(view: LoginContract.View) {       // takeView : View와 Presenter를 연결해준다. (loginView = view)
        loginView = view
    }

    override fun dropView() {       // View가 제거된것을 Presenter에 알려준다.
        loginView = null
    }

    // 로그인
    // view에서 입력한 email과 pawword를 받아와 db에 저장된 정보와 일치하는지 확인하는 함수
    override fun logIn(context: Context, inputEmail: String, inputPw: String) {
        val client: OkHttpClient = RetrofitClient.getClient(context, "receiveCookie")
        val retrofitInterface = RetrofitClient.retrofitInterface(client)

        retrofitInterface.logIn(inputEmail, inputPw).enqueue(object : Callback<GetUser> {
            override fun onResponse(call: Call<GetUser>, response: Response<GetUser>) {
                if (response.isSuccessful) {
                    Log.e("Success", Gson().toJson(response.body()))

                    getUser(context)
                    loginView!!.startMainActivity()
                    if(response.body()?.token == null){
                        Toast.makeText(context, "아이디 또는 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
                    } else {
//                        Log.e("token not null", response.body()!!.token)
                    }

                } else {
                    Log.e("unSuccess", Gson().toJson(response.errorBody()))
                    Toast.makeText(context, "아이디 또는 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
                }

            }
            override fun onFailure(call: Call<GetUser>, t: Throwable) {
                Log.e("Login Fail", t.toString())
                getUser(context)
                loginView!!.startMainActivity()

            }
        })

    }

    override fun getUser(context: Context) {
        val client: OkHttpClient = RetrofitClient.getClient(context, "addCookie")
        val retrofitInterface = RetrofitClient.retrofitInterface(client)

        retrofitInterface.getUser().enqueue(object : Callback<userData>{

            override fun onResponse(call: Call<userData>, response: Response<userData>) {
                if(response.isSuccessful){
                    Log.e("Success", Gson().toJson(response.body()))

                }else{

                }
            }

            override fun onFailure(call: Call<userData>, t: Throwable) {
                Log.e("getUser", t.toString())
            }
        })
    }


    override fun logout() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}