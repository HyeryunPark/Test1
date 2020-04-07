package com.example.saveme.missing.createmissing

class MissingReportPresenter : MissingReportContract.Presenter{

    private var missingReportView: MissingReportContract.View? = null

    override fun takeView(view: MissingReportContract.View) {
        missingReportView = view
    }

    override fun dropView() {
        missingReportView = null
    }

    override fun choosePicture() {

    }

}