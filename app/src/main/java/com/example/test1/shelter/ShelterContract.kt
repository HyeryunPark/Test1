package com.example.test1.shelter

import com.example.test1.base.BasePresenter
import com.example.test1.base.BaseView

interface ShelterContract {

    interface View : BaseView {

    }

    interface Presenter : BasePresenter<View> {

        // 공공데이터 openApi 사용하는 함수
        fun openApiTask()
    }
}