package com.example.saveme.home

import android.content.Context
import com.example.saveme.base.BasePresenter
import com.example.saveme.base.BaseView
import com.example.saveme.community.CommunityModel

interface HomeContract {

    interface View:BaseView{

        fun bottomNavigationView()
    }
    interface Presenter : BasePresenter<View>{

        fun loadItems(list: ArrayList<CommunityModel>, context: Context)
    }

}