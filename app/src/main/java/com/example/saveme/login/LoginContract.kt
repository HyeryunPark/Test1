package com.example.saveme.login

import com.example.saveme.base.BasePresenter
import com.example.saveme.base.BaseView

interface LoginContract {

    interface View : BaseView {
        fun showLoading()                       // showLoading(), hideLoading() : 데이터를 받아서 정제 하는동안 보일 ProgressBar를 관리하는 함수
        fun hideLoading()

        fun startMainActivity()
    }

    interface Presenter : BasePresenter<View> {
        fun getUserList()                       // Model로부터 데이터를 받아오기 (정제하기)위한 함수

        // LoginPresenter에서 사용자가 입력한 email과 password가 일치하는지 확인하는 함수
        fun checkLoginUser(inputEmail: String, inputPw: String)

        fun logout()    // 사용자 로그아웃
    }
}