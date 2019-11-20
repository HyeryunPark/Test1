package com.example.test1.signup

import com.example.test1.base.BasePresenter
import com.example.test1.base.BaseView
import com.example.test1.model.User

interface SignUpContract {

    interface View : BaseView {

        fun startLoginActivity()    // SignUpActivity에서 LoginActivity로 이동하는 함수
    }

    interface Presenter : BasePresenter<View> {

        fun insertUserData(user: User)  // SignUpPresenter에서 유저정보를 저장하는 함수
    }

}