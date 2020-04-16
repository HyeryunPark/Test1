package com.example.saveme.home

class HomePresenter : HomeContract.Presenter {

    private var homeView: HomeContract.View? = null

    override fun takeView(view: HomeContract.View) {
        homeView = view
    }

    override fun dropView() {
        homeView = null
    }
}