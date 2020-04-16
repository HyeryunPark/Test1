package com.example.saveme.community

import com.example.saveme.base.BasePresenter
import com.example.saveme.base.BaseView

interface CommunityContract {

    interface View : BaseView {

        fun bottomNavigationView()

    }

    interface Presenter : BasePresenter<View> {

    }
}