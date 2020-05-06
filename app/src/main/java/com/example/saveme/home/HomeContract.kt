package com.example.saveme.home

import com.example.saveme.base.BasePresenter
import com.example.saveme.base.BaseView

interface HomeContract {

    interface View:BaseView{

        fun bottomNavigationView()
    }
    interface Presenter : BasePresenter<View>{

    }

}