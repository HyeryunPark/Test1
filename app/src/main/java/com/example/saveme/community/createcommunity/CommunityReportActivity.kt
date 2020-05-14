package com.example.saveme.community.createcommunity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import com.example.saveme.R
import com.example.saveme.base.BaseActivity
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.soundcloud.android.crop.Crop
import kotlinx.android.synthetic.main.activity_community_report.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.ArrayList

class CommunityReportActivity : BaseActivity(), CommunityReportContract.View {

    private lateinit var communityReportPresenter: CommunityReportPresenter

    private var isPermission = true
    private var isCamera: Boolean = false

    var photo2: String? = null
    var photo3: String? = null

    private val PICK_FROM_ALBUM = 1
    private val PICK_FROM_CAMERA = 2

    private var tempFile: File? = null
    // 받아온 이미지를 저장할 변수
    var lastUri: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_report)

        pressedRegistrationBtn()

        community_report_img1.setOnClickListener {
            tedPermission()
        }

        tv_community_report_category.setOnClickListener {
            val items = arrayOf("반려이야기", "입양/재회 후기", "입양해주세요", "임시호보요청")
            val builder = AlertDialog.Builder(this)
            with(builder) {
                setTitle("카테고리 선택")
                setItems(items) { dialogInterface, i ->
                    tv_community_report_category.text = items[i]
                }
                show()
            }
        }
    }

    override fun initPresenter() {
        communityReportPresenter = CommunityReportPresenter()
    }

    private fun pressedRegistrationBtn() {
        btn_community_report_registration.setOnClickListener {

            val intent = Intent()
            if (tv_community_report_category.text == "카테고리를 선택해주세요.") {
                Toast.makeText(this, "카테고리를 선택해주세요", Toast.LENGTH_SHORT).show()
            } else if (et_community_report_title.text.isEmpty() && et_community_report_content.text.isEmpty()) {
                Toast.makeText(this, "비어있는 칸을 채워주세요", Toast.LENGTH_SHORT).show()
            } else {
                intent.putExtra("user_id", 0)
                intent.putExtra("community_category", tv_community_report_category.text.toString())
                intent.putExtra("community_title", et_community_report_title.text.toString())
                intent.putExtra("community_content", et_community_report_content.text.toString())
                intent.putExtra("img1", lastUri)
                intent.putExtra("img2", photo2.toString())
                intent.putExtra("img3", photo3.toString())

                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
    }

    private fun tedPermission() {

        var permissionListener: PermissionListener = object : PermissionListener {

            override fun onPermissionGranted() {
                // 권한 요청 성공
                isPermission = true
                cameraDialog()
            }

            override fun onPermissionDenied(deniedPermissions: ArrayList<String>?) {
                // 권한 요청 실패
                isPermission = false
            }
        }

        // 권한 체크하는 메소드
        TedPermission.with(this)
            .setPermissionListener(permissionListener)
            .setRationaleMessage(R.string.permission_2)
            .setDeniedMessage(R.string.permission_1)
            .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
            .check()

    }

    private fun cameraDialog() {
        val builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.board_camera_dialog, null)

        val camera = dialogView.findViewById<LinearLayout>(R.id.camera)
        val album = dialogView.findViewById<LinearLayout>(R.id.album)
        val externalClick = dialogView.findViewById<LinearLayout>(R.id.camera_dialog)

        val alertDialog = builder.create()
        alertDialog.setView(dialogView)
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()

        // 카메라 선택
        camera.setOnClickListener {
            if (isPermission) cameraClick()
            else Toast.makeText(this, R.string.permission_2, Toast.LENGTH_SHORT).show()
            alertDialog.dismiss()
        }

        // 앨범 선택
        album.setOnClickListener {
            if (isPermission) albumClick()
            else Toast.makeText(this, R.string.permission_2, Toast.LENGTH_SHORT).show()
            alertDialog.dismiss()
        }

        // 취소
        externalClick.setOnClickListener {
            alertDialog.dismiss()
        }
    }

    override fun albumClick() {
        isCamera = false
        val intentAlbum = Intent(Intent.ACTION_PICK)

        tempFile = communityReportPresenter.createImageFile()
        try {

        } catch (e: IOException) {
            Toast.makeText(this, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
            finish()
            e.printStackTrace()
        }
        if (tempFile != null) {
            val photoUri =
                FileProvider.getUriForFile(this, "com.example.saveme.provider", tempFile!!)
            Log.e("앨범에서 가져온 photoUri", photoUri.toString())
        }
        intentAlbum.type = MediaStore.Images.Media.CONTENT_TYPE
        startActivityForResult(intentAlbum, PICK_FROM_ALBUM)
    }

    override fun cameraClick() {
        isCamera = true
        val intentCamera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        tempFile = communityReportPresenter.createImageFile()
        try {

        } catch (e: IOException) {
            Toast.makeText(this, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
            finish()
            e.printStackTrace()
        }

        if (tempFile != null) {
            val photoUri =
                FileProvider.getUriForFile(this, "com.example.saveme.provider", tempFile!!)
            intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            startActivityForResult(intentCamera, PICK_FROM_CAMERA)
        }
    }

    private fun cropImage(photoUri: Uri) {
        Log.e("tempFile : ", tempFile.toString())
        // 갤러리에서 가져온 사진을 크롭화면을 보낸다
        // 갤러리에서 선택한 경우에는 tempFile 이 없으므로 새로 생성해준다
        if (tempFile == null) {
            try {
                tempFile = communityReportPresenter.createImageFile()
            } catch (e: IOException) {
                Toast.makeText(this, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                finish()
                e.printStackTrace()
            }
        }

        // 크롭 후 저장할 Uri
        val saveUri = Uri.fromFile(tempFile)
        //  사진촬영은 tempFile 이 만들어져 있으니 넣어서 저장하면 된다.
        // 하지만 갤러리는 크롭후에 이미지를 저장할 파일이 없기 때문에 위의 코드를 추가로 작성해줘야 한다.

        lastUri = tempFile!!.absolutePath // 최정적으로 저장될 uri
        Log.e("최종 lastUri", lastUri)

        Crop.of(photoUri, saveUri).asSquare().start(this)

    }

    /*
        tempFile 을 bitmap 으로 변환 후 ImageView 에 설정한다.
    */
    private fun setImage() {
        try {
//            ImageResizeUtils.resizeFile(tempFile!!, tempFile, 1280, isCamera)

            val options = BitmapFactory.Options()
            val originalBm = BitmapFactory.decodeFile(tempFile!!.absolutePath, options)
            val resizedBitmap = Bitmap.createScaledBitmap(originalBm, 100, 100, true) // 이미지 사이즈 조정
            Log.e("setImage", "" + tempFile!!.absolutePath)

            community_report_img1.setImageBitmap(resizedBitmap)

            /*
            tempFile 사용 후 null 처리를 해줘야 한다.
            (resultCode != RESULT_OK) 일 때 tempFile 을 삭제하기 때문에
            기존에 데이터가 남아 있게 되면 원치 않은 삭제가 이뤄진다.
            */

//            tempFile = null
        } catch (e: Exception) {
            Log.e("REQUEST_CROP", "missing 크롭오류 : $e")
            finish()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show()

            if (tempFile != null) {
                if (tempFile!!.exists()) {
                    if (tempFile!!.delete()) {
                        Log.e("이미지 등록 취소", tempFile!!.absolutePath + " 삭제 성공")
                        tempFile = null
                    }
                }
            }
        }

        when (requestCode) {
            PICK_FROM_ALBUM -> {   // 앨범에서 온 경우
                Log.e("PICK_FROM_ALBUM", "앨범선택후")
                val photoUri = data!!.data

                if (photoUri != null) {
                    cropImage(photoUri)
                }

//                setImage()
            }
            PICK_FROM_CAMERA -> {  // 카메라에서 온 경우
                Log.e("PICK_FROM_CAMERA", "카메라 선택후")
                try {

                    val bitmap =
                        MediaStore.Images.Media.getBitmap(contentResolver, Uri.fromFile(tempFile))
                    val ei = ExifInterface(tempFile?.absolutePath)
                    val orientation = ei.getAttributeInt(
                        ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_UNDEFINED
                    )
                    val rotatedBitmap = communityReportPresenter.rotateImage(bitmap, 90.toFloat())
                    community_report_img1.setImageBitmap(rotatedBitmap)
                    var newFile = File(tempFile?.absolutePath)
                    newFile.createNewFile()
                    var out: OutputStream? = null
                    out = FileOutputStream(newFile)
                    if (rotatedBitmap != null) {
                        rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
                    } else {
                        Toast.makeText(this, "이미지 처리 오류!", Toast.LENGTH_SHORT).show()
                    }
                    Log.e("isCamera", isCamera.toString())
                    val photoUri = Uri.fromFile(tempFile)
                    cropImage(photoUri)
                } catch (e: Exception) {
                    Log.e("PICK_FROM_CAMERA", "카메라오류")
                }

//                setImage()
            }
            Crop.REQUEST_CROP -> {
                setImage()
            }
        }


    }

    override fun showError(error: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showToastMessage(msg: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
