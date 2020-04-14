package com.example.saveme.shelter

import android.content.Context
import com.example.saveme.base.BasePresenter
import com.example.saveme.base.BaseView

interface ShelterContract {

    interface View : BaseView {

    }

    interface Presenter : BasePresenter<View> {

        // 공공데이터 openApi 사용하는 함수
        fun openApiTask()

        // 서버에서 파싱한 데이터 가져오는 함수
        fun getAbandonedPetsParsing(adapter: ShelterAdapter, list: ArrayList<ShelterModel>, context: Context)
    }
}