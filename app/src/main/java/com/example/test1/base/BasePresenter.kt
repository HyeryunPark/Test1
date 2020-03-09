package com.example.test1.base

interface BasePresenter<T>{

    fun takeView(view: T)       // View가 생성 혹은 bind 될 때를 Presenter에 전달
    fun dropView()              // View가 제거되거나 unBind 될 때를 Presenter에 전달z

}