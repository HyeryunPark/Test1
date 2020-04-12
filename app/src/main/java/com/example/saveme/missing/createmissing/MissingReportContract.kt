package com.example.saveme.missing.createmissing

import android.graphics.Bitmap
import com.example.saveme.base.BasePresenter
import com.example.saveme.base.BaseView
import java.io.File

interface MissingReportContract {

    interface View : BaseView {

        fun albumClick()
        fun cameraClick()

        fun setData()
    }

    interface Presenter : BasePresenter<View> {

        fun createImageFile(): File
        fun rotateImage(source: Bitmap, angle: Float): Bitmap
    }
}