package com.example.saveme.mypage

import com.example.saveme.base.BasePresenter
import com.example.saveme.base.BaseView

interface MypageContract {

    interface View : BaseView {

        fun bottomNavigationView()

    }

    interface Presenter : BasePresenter<View> {

    }
}