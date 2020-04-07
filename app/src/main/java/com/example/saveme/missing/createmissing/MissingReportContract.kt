package com.example.saveme.missing.createmissing

import com.example.saveme.base.BasePresenter
import com.example.saveme.base.BaseView

interface MissingReportContract {

    interface View : BaseView {

        fun setData()
    }

    interface Presenter : BasePresenter<View> {

        fun choosePicture()
    }
}