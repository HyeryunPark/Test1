package com.example.saveme.shelter

class ShelterPresenter : ShelterContract.Presenter {

    private var shelterView: ShelterContract.View? = null

    override fun takeView(view: ShelterContract.View) { // view 와 presenter 를 연결해줌
        shelterView = view
    }

    override fun dropView() {   // view 가 제거된 것을 presenter 에 알려준다.
        shelterView = null
    }

    override fun openApiTask() {    // 공공데이터포털 오픈API를 사용하는 함수

    }


}