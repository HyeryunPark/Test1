package com.example.saveme

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.board_camera_dialog.*
import java.io.File


class CameraDialog : AppCompatActivity() {

    private val PICK_FROM_ALBUM = 1
    private val PICK_FROM_CAMERA = 2
    private val CROP_FROM_CAMERA = 3

    private var imagePath = ""
    lateinit var uri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.board_camera_dialog)

        externalClick()
        albumClick()
        cameraClick()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK)
            return

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PICK_FROM_ALBUM -> {    // 앨범선택 후 돌아왔을때
                    val uri = data?.data
                    bresultData(uri)
                }
                PICK_FROM_CAMERA -> {   // 카메라 선택 후 돌아왔을때
                    bresultData(uri)
                }
                CROP_FROM_CAMERA -> {   // 이미지를 크롭
                    cropImage()
                }
            }
        }

    }

    private fun externalClick() {    // 목록 이외에 다른 곳을 눌렀을 때
        camera_dialog.setOnClickListener {
            onBackPressed()
        }
    }

    private fun albumClick() {   // 앨범 선택했을 때
        album.setOnClickListener {
            val intentAlbum = Intent(Intent.ACTION_PICK)
            intentAlbum.type = MediaStore.Images.Media.CONTENT_TYPE
            startActivityForResult(intentAlbum, PICK_FROM_ALBUM)
        }
    }

    private fun cameraClick() { // 카메라 선택했을 때
        camera.setOnClickListener {
            showCamera()
        }
    }

    private fun showCamera() {
        uri = getFileUri()
        val intentCamera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        startActivityForResult(intentCamera, PICK_FROM_CAMERA)
    }

    private fun getFileUri(): Uri {
        var dir = File(this.filesDir, "img")
        if (!dir.exists()) {
            dir.mkdir()
        }
        var file = File(dir, System.currentTimeMillis().toString() + "png")
        imagePath = file.absolutePath

        var temp = File(imagePath)

        return FileProvider.getUriForFile(this, "com.example.saveme", temp)
    }

    private fun cropImage() {
        var cropIntent = Intent("com.android.camera.action.CROP")
        cropIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        cropIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        //indicate image type and Uri of image
        cropIntent.setDataAndType(uri, "image/*")
        //set crop properties
        cropIntent.putExtra("crop", "true")
        cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        startActivityForResult(cropIntent, CROP_FROM_CAMERA)
    }

    fun bresultData(uri: Uri?) {
        var intent = Intent()
        intent.putExtra("image", this.uri.toString())
        setResult(Activity.RESULT_OK, intent)
        finish()
    }


}
