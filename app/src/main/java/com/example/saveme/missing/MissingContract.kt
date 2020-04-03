package com.example.saveme.missing

import android.content.Context
import com.example.saveme.base.BasePresenter
import com.example.saveme.base.BaseView

interface MissingContract {

    interface View : BaseView {

        fun refresh()
    }

    interface Presenter : BasePresenter<View> {

        fun loadItems(adapter: MissingAdapter, list: ArrayList<MissingModel>, context: Context)

        fun addItems(status: String, date: String, city: String, district: String, detailLocation: String, phone: String, species: String,
            breed: String, gender: String, neuter: Boolean, age: String, weight: String, pattern: String, feature: String, etc: String,
                     context: Context, adapter: MissingAdapter, list: ArrayList<MissingModel>)

        fun updateItems()

        fun deleteItems(pk: Int, context: Context)

    }
}