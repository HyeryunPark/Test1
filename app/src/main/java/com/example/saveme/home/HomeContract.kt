package com.example.saveme.home

import android.content.Context
import com.example.saveme.base.BasePresenter
import com.example.saveme.base.BaseView
import com.example.saveme.community.CommunityModel

interface HomeContract {

    interface View:BaseView{

        fun bottomNavigationView()

        fun setAdoptItems1(title: String, img1: String)
        fun setAdoptItems2(title: String, img1: String)
        fun setAdoptItems3(title: String, img1: String)
        fun setProtectionItems1(title: String, img1: String)
        fun setProtectionItems2(title: String, img1: String)
        fun setProtectionItems3(title: String, img1: String)
    }
    interface Presenter : BasePresenter<View>{

        fun loadItems(list: ArrayList<CommunityModel>, context: Context)
    }

}