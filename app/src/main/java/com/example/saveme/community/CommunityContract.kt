package com.example.saveme.community

import android.content.Context
import com.example.saveme.base.BasePresenter
import com.example.saveme.base.BaseView
import com.example.saveme.model.CreateCommunity

interface CommunityContract {

    interface View : BaseView {

        fun bottomNavigationView()
        fun refresh()
    }

    interface Presenter : BasePresenter<View> {
        fun loadItems(adapter: CommunityAdapter, list: ArrayList<CommunityModel>, context: Context)

        fun addItems(createCommunity: CreateCommunity, context: Context, adapter: CommunityAdapter, list: ArrayList<CommunityModel>)

        fun updateItems(pk: Int, communityModel: CommunityModel, context: Context, adapter: CommunityAdapter, list: ArrayList<CommunityModel>)

        fun deleteItems(pk: Int, context: Context)
    }
}