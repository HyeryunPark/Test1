package com.example.saveme.community

class CommunityPresenter : CommunityContract.Presenter{

    private var communityView: CommunityContract.View? = null

    override fun takeView(view: CommunityContract.View) {
        communityView = view
    }

    override fun dropView() {
        communityView = null
    }
}