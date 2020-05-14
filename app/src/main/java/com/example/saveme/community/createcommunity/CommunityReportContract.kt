package com.example.saveme.community.createcommunity

import android.graphics.Bitmap
import com.example.saveme.base.BasePresenter
import com.example.saveme.base.BaseView
import java.io.File

interface CommunityReportContract {

    interface View : BaseView {

        fun albumClick()
        fun cameraClick()


    }

    interface Presenter : BasePresenter<View> {

        fun createImageFile(): File
        fun rotateImage(source: Bitmap, angle: Float): Bitmap
    }
}