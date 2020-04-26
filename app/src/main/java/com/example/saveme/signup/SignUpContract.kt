package com.example.saveme.signup

import android.content.Context
import com.example.saveme.base.BasePresenter
import com.example.saveme.base.BaseView
import com.example.saveme.model.User

interface SignUpContract {

    interface View : BaseView {

        fun startLoginActivity()    // SignUpActivity에서 LoginActivity로 이동하는 함수
    }

    interface Presenter : BasePresenter<View> {

        fun insertUserData(user: User, context: Context)  // SignUpPresenter에서 유저정보를 저장하는 함수
    }

}