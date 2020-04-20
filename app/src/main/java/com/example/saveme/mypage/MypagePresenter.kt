package com.example.saveme.mypage

class MypagePresenter : MypageContract.Presenter {

    private var mypageView : MypageContract.View? = null

    override fun takeView(view: MypageContract.View) {
        mypageView = view
    }

    override fun dropView() {
        mypageView = null
    }
}