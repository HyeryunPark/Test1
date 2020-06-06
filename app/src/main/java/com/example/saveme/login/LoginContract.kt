package com.example.saveme.login

import android.content.Context
import com.example.saveme.base.BasePresenter
import com.example.saveme.base.BaseView

interface LoginContract {

    interface View : BaseView {
        fun showLoading()                       // showLoading(), hideLoading() : 데이터를 받아서 정제 하는동안 보일 ProgressBar를 관리하는 함수
        fun hideLoading()

        fun startMainActivity()
    }

    interface Presenter : BasePresenter<View> {

        fun logIn(context: Context, inputEmail: String, inputPw: String)   // LoginPresenter에서 사용자가 입력한 email과 password가 일치하는지 확인하는 함수
        fun getUser(context: Context)   // 사용자 정보 가져오기
        fun logout()    // 사용자 로그아웃
    }
}