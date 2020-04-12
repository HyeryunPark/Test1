package com.example.saveme.missing

import android.content.Context
import com.example.saveme.base.BasePresenter
import com.example.saveme.base.BaseView
import com.example.saveme.model.CreateMissing

interface MissingContract {

    interface View : BaseView {

        fun refresh()

        fun modifyActivity(id: Int, missingModel: MissingModel)
    }

    interface Presenter : BasePresenter<View> {

        fun loadItems(adapter: MissingAdapter, list: ArrayList<MissingModel>, context: Context)

        fun addItems(createMissing: CreateMissing, context: Context, adapter: MissingAdapter, list: ArrayList<MissingModel>)

        fun updateItems(pk: Int, missingModel: MissingModel, context: Context, adapter: MissingAdapter, list: ArrayList<MissingModel>)

        fun deleteItems(pk: Int, context: Context)

        fun modifyActivity(id: Int, missingModel: MissingModel)

    }
}