package com.example.saveme.login

import android.content.Context
import android.os.Handler
import android.util.Log
import com.example.saveme.network.Json_User
import com.example.saveme.network.RetrofitClient
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPresenter : LoginContract.Presenter {

    private var loginView : LoginContract.View? = null
//    private val retrofitInterface = RetrofitClient.retrofitInterface

    override fun takeView(view: LoginContract.View) {       // takeView : View와 Presenter를 연결해준다. (loginView = view)
        loginView = view
    }

    override fun dropView() {       // View가 제거된것을 Presenter에 알려준다.
        loginView = null
    }

    override fun getUserList() {
//        searchView?.showLoading()   // 1초간 네트워크와 통신하는 척을 해주었다. (View에 프로그레스바를 보여주도록 요청한다.)

        Handler().postDelayed({
//            searchView?.showUserList(userList)              // Model에서 전달받은 데이터를 View에게 전달한다.
//            searchView?.hideLoading()                       // 네트워크통신이 끝났으니 View에 프로그레스바를 숨기도록 요청한다.
        },1000)
    }


    //----------------------------------------------------------------------------------------------------------------------------


    // 로그인
    // view에서 입력한 email과 pawword를 받아와 db에 저장된 정보와 일치하는지 확인하는 함수
    override fun checkLoginUser(context: Context, inputEmail: String, inputPw: String) {
        val client: OkHttpClient = RetrofitClient.getClient(context, "")
        val retrofitInterface = RetrofitClient.retrofitInterface(client)

        retrofitInterface.logIn(inputEmail, inputPw).enqueue(object : Callback<List<Json_User>>{
            override fun onResponse(call: Call<List<Json_User>>, response: Response<List<Json_User>>) {
                if (response.isSuccessful){
                    Log.e("Success", Gson().toJson(response.body()))
                    if(response.body() != null){
                        if(response.body()!!.isEmpty()){
                            loginView?.showToastMessage("존재하지 않는 계정입니다.")
                            Log.e("계정없음","존재하지않는 이메일")
                        }else{

                            if(inputEmail == response.body()!!.get(0).userEmail && inputPw == response.body()!!.get(0).userPw){
                                // 이메일, 비밀번호 일치
                                loginView?.showToastMessage("계정정보일치")
                                loginView?.startMainActivity()
                                Log.e("이메일 일치","비밀번호 일치")

                            }else if(inputEmail == response.body()!!.get(0).userEmail && inputPw != response.body()!!.get(0).userPw){
                                // 비밀번호 불일치
                                loginView?.showToastMessage("비밀번호가 잘못되었습니다.")
                                Log.e("이메일 일치","비밀번호 불일치")

                            }
                        }
                    }
                }
                else
                    Log.e("unSuccess", Gson().toJson(response.errorBody()))
            }

            override fun onFailure(call: Call<List<Json_User>>, t: Throwable) {
                Log.e("Login Fail",t.toString())
            }

        })

    }



    override fun logout() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}