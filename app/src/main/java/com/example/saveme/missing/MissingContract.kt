package com.example.saveme.missing

import com.example.saveme.base.BasePresenter
import com.example.saveme.base.BaseView

interface MissingContract {

    interface View : BaseView {

        fun refresh()
    }

    interface Presenter : BasePresenter<View> {

        fun loadItems(adapter: MissingAdapter, list: ArrayList<MissingModel>)

        fun addItems()

        fun updateItems()

        fun deleteItems()

    }
}