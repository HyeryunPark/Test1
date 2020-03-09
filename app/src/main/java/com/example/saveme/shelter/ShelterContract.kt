package com.example.saveme.shelter

import com.example.saveme.base.BasePresenter
import com.example.saveme.base.BaseView

interface ShelterContract {

    interface View : BaseView {

    }

    interface Presenter : BasePresenter<View> {

        // 공공데이터 openApi 사용하는 함수
        fun openApiTask()
    }
}