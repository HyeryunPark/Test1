package com.example.saveme.popup

import android.content.Context
import com.example.saveme.base.BasePresenter
import com.example.saveme.base.BaseView

interface PopUpContract {

    interface View: BaseView {
        fun setNewsData1(title: String)
        fun setNewsData2(title: String)
        fun setNewsData3(title: String)
        fun setNewsData4(title: String)
        fun setNewsData5(title: String)
    }

    interface Presenter: BasePresenter<View> {

        fun loadNews(context: Context)
    }
}