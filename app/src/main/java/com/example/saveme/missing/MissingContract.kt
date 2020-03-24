package com.example.saveme.missing

import com.example.saveme.base.BasePresenter
import com.example.saveme.base.BaseView

interface MissingContract {

    interface View : BaseView {

        fun refresh()
    }

    interface Presenter : BasePresenter<View> {

        fun loadItems(adapter: MissingAdapter, list: ArrayList<MissingModel>)

        fun addItems(
            status: String,
            date: String,
            city: String,
            district: String,
            detailLocation: String,
            phone: String,
            species: String,
            breed: String,
            gender: String,
            neuter: String,
            age: String,
            weight: String,
            pattern: String,
            feature: String,
            etc: String
        )

        fun updateItems()

        fun deleteItems()

    }
}