package com.example.saveme.mypage

import android.content.Context
import com.example.saveme.base.BasePresenter
import com.example.saveme.base.BaseView

interface MypageContract {

    interface View : BaseView {

        fun bottomNavigationView()

    }

    interface Presenter : BasePresenter<View> {

        fun logout(context: Context, token: String)
    }
}