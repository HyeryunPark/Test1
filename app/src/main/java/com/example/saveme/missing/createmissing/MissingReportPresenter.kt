package com.example.saveme.missing.createmissing

import android.graphics.Bitmap
import android.graphics.Matrix
import android.os.Environment
import android.util.Log
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class MissingReportPresenter : MissingReportContract.Presenter {

    private var missingReportView: MissingReportContract.View? = null


    override fun takeView(view: MissingReportContract.View) {
        missingReportView = view
    }

    override fun dropView() {
        missingReportView = null
    }

    override fun createImageFile(): File {

        // 이미지 파일 이름 ( SaveMe_{시간}_ )
        val timeStamp = SimpleDateFormat("HHmmss").format(Date())
        val imageFileName = "saveMe_" + timeStamp + "_"

        // 이미지가 저장될 폴더 이름 ( SaveMe )
        val storageDir = File(Environment.getExternalStorageDirectory().toString() + "/saveMe/")
        // 폴더가 없을 경우 새로 생성한다
        if (!storageDir.exists()) {
            storageDir.mkdir()
        }

        // 빈 파일 생성
        val image = File.createTempFile(imageFileName, ".jpg", storageDir)
        Log.e("이미지 파일 생성", "createImageFile" + image.absolutePath)


        return image
    }

    override fun rotateImage(source: Bitmap, angle: Float): Bitmap {    // 이미지 회전해주는 코드
        val matrix = Matrix()
        matrix.postRotate(90F)
        return Bitmap.createBitmap(
            source, 0, 0, source.width, source.height,
            matrix, true
        )
    }

}