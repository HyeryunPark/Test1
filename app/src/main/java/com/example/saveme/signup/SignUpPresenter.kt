package com.example.saveme.signup

import android.util.Log
import com.example.saveme.model.User
import com.example.saveme.network.RetrofitClient
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpPresenter : SignUpContract.Presenter {

    private var signUpView : SignUpContract.View? = null
//    private val retrofitInterface = RetrofitClient.retrofitInterface

    // takeView : View와 Presenter를 연결해준다. (signUpView = view)
    override fun takeView(view: SignUpContract.View) {
        signUpView = view
    }

    // 회원가입
    override fun insertUserData(user: User) {

        /*retrofitInterface.signUp(user.email, user.name, user.pw).enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.e("SignUp Success", Gson().toJson(response.body()))
                signUpView?.showToastMessage("회원가입완료")  // 회원가입완료됐다는 메세지 띄우고
                signUpView?.startLoginActivity()                    // 로그인화면으로 이동

            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("SignUp Fail",t.toString())
                signUpView?.showToastMessage("회원가입에 실패하였습니다.")
            }

        })*/

    }

    override fun dropView() {
        signUpView = null
    }

}