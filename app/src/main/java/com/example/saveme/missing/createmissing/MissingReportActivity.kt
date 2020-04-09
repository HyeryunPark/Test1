package com.example.saveme.missing.createmissing

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
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
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import com.example.saveme.R
import com.example.saveme.base.BaseActivity
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.soundcloud.android.crop.Crop
import kotlinx.android.synthetic.main.activity_missing_report.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class MissingReportActivity : BaseActivity(), MissingReportContract.View {

    private lateinit var missingReportPresenter: MissingReportPresenter
    var modifyId = -1

    private var isPermission = true

    var photo1: String? = null
    var photo2: String? = null
    var photo3: String? = null

    private val PICK_FROM_ALBUM = 1
    private val PICK_FROM_CAMERA = 2

    private var tempFile: File? = null
    // 받아온 이미지를 저장할 변수
    var lastUri: String? = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_missing_report)

        // 툴바선언
        setSupportActionBar(toolbar_missing_report)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)  // 왼쪽버튼 사용 여부 true
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_pink_24dp)   // 왼쪽버튼 이미지 설정
        supportActionBar!!.setDisplayShowTitleEnabled(false)    // 기본 타이틀 사용 여부 false

        setData()
        pressedRegistrationBtn()

        // 상태(실종, 보호, 목격, 완료)
        missing_info_status.setOnClickListener {
            val items = arrayOf("실종", "보호", "목격", "완료")
            val builder = AlertDialog.Builder(this)
            with(builder) {
                setTitle("상태 선택")
                setItems(items) { dialogInterface, i ->
                    missing_info_status.text = items[i]
                }
                show()
            }
        }

        // 날짜
        missing_info_date.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)


            val dpd = DatePickerDialog(
                this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                    val month = monthOfYear + 1
                    val date = "$year - $month - $dayOfMonth"
                    missing_info_date.text = date

                },
                year,
                month,
                day
            )
            dpd.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dpd.show()
        }

        // 도시
        missing_info_city.setOnClickListener {
            val items = arrayOf(
                "경기도",
                "대구광역시",
                "대전광역시",
                "세종특별자치지",
                "인천광역시",
                "전라남도",
                "강원도",
                "울산광역시",
                "전라북도",
                "충청남도",
                "제주특별자치도",
                "경상남도",
                "경상북도",
                "충청북도",
                "모든 지역",
                "서울특별시",
                "광주광역시",
                "부산광역시"
            )
            val builder = AlertDialog.Builder(this)
            with(builder) {
                setTitle("도시 선택")
                setItems(items) { dialogInterface, i ->
                    missing_info_city.text = items[i]
                }
                show()
            }
        }
        // 시군구
        if (missing_info_city.text !== "모든 지역") {
            missing_info_district.visibility = View.VISIBLE
            missing_info_district.setOnClickListener {
                val items = arrayOf(
                    "성북구",
                    "마포구"
                )
                val builder = AlertDialog.Builder(this)
                with(builder) {
                    setTitle("시군구 선택")
                    setItems(items) { dialogInterface, i ->
                        missing_info_district.text = items[i]
                    }
                    show()
                }
            }
        }
        // 구체적인 장소
        missing_info_detail_location

        // 작성자 연락처
        missing_info_phone

        // 종(개,고양이,기타)
        missing_info_species.setOnClickListener {
            val items = arrayOf("개", "기타", "모든동물", "고양이")
            val builder = AlertDialog.Builder(this)
            with(builder) {
                setTitle("동물 선택")
                setItems(items) { dialogInterface, i ->
                    missing_info_species.text = items[i]
                }
                show()
            }
        }
        // 품종
        if (missing_info_species.text !== "모든 동물") {
            missing_info_breed.visibility = View.VISIBLE
            missing_info_breed.setOnClickListener {
                val items: Array<String>
                if (missing_info_species.text == "개") {
                    items = arrayOf("전체", "말티즈", "푸들")
                    val builder = AlertDialog.Builder(this)
                    with(builder) {
                        setTitle("품종 선택")
                        setItems(items) { dialogInterface, i ->
                            missing_info_breed.text = items[i]
                        }
                        show()
                    }
                } else if (missing_info_species.text == "고양이") {
                    items = arrayOf("전체", "코리안숏헤어")
                    val builder = AlertDialog.Builder(this)
                    with(builder) {
                        setTitle("품종 선택")
                        setItems(items) { dialogInterface, i ->
                            missing_info_breed.text = items[i]
                        }
                        show()
                    }
                } else if (missing_info_species.text == "기타") {
                    items = arrayOf("기타축종")
                    val builder = AlertDialog.Builder(this)
                    with(builder) {
                        setTitle("품종 선택")
                        setItems(items) { dialogInterface, i ->
                            missing_info_breed.text = items[i]
                        }
                        show()
                    }
                }
            }
        }

        // 성별
        missing_info_gender.setOnClickListener {
            val items = arrayOf("미확인", "수컷", "암컷")
            val builder = AlertDialog.Builder(this)
            with(builder) {
                setTitle("성별 선택")
                setItems(items) { dialogInterface, i ->
                    missing_info_gender.text = items[i]
                }
                show()
            }
        }

        // 중성화 여부
        missing_info_neuter.isChecked


        // 나이
        missing_info_age.setOnClickListener {
            val items = arrayOf("나이 모름", "1년 미만", "1살", "2살", "3살")
            val builder = AlertDialog.Builder(this)
            with(builder) {
                setTitle("나이 선택")
                setItems(items) { dialogInterface, i ->
                    missing_info_age.text = items[i]
                }
                show()
            }
        }

        // 몸무게
        missing_info_weight.setOnClickListener {
            val items = arrayOf("몸무게 모름", "1kg 미만", "1kg", "2kg", "3kg")
            val builder = AlertDialog.Builder(this)
            with(builder) {
                setTitle("몸무게 선택")
                setItems(items) { dialogInterface, i ->
                    missing_info_weight.text = items[i]
                }
                show()
            }
        }

        // 털 색
        missing_info_pattern

        // 특징
        missing_info_feature

        // 기타
        missing_info_etc

        missing_info_photo1.setOnClickListener {
            tedPermission()
        }
    }


    override fun initPresenter() {
        missingReportPresenter = MissingReportPresenter()
    }

    private fun pressedRegistrationBtn() {
        btn_missing_report_registration.setOnClickListener {

            val intent = Intent()
            if (missing_info_detail_location.text.isEmpty() && missing_info_phone.text.isEmpty() && missing_info_pattern.text.isEmpty() && missing_info_feature.text.isEmpty() && missing_info_etc.text.isEmpty()) {
                Toast.makeText(this, "비어있는 칸을 채워주세요", Toast.LENGTH_SHORT).show()
            } else {
                if (modifyId != -1) {
                    intent.putExtra("id", modifyId)
                }
                intent.putExtra("status", missing_info_status.text.toString())
                intent.putExtra("date", missing_info_date.text.toString())
                intent.putExtra("city", missing_info_city.text.toString())
                intent.putExtra("district", missing_info_district.text.toString())
                intent.putExtra("detail_location", missing_info_detail_location.text.toString())
                intent.putExtra("phone", missing_info_phone.text.toString())
                intent.putExtra("species", missing_info_species.text.toString())
                intent.putExtra("breed", missing_info_breed.text.toString())
                intent.putExtra("gender", missing_info_gender.text.toString())
                intent.putExtra("neuter", false)
                intent.putExtra("age", missing_info_age.text.toString())
                intent.putExtra("weight", missing_info_weight.text.toString())
                intent.putExtra("pattern", missing_info_pattern.text.toString())
                intent.putExtra("feature", missing_info_feature.text.toString())
                intent.putExtra("etc", missing_info_etc.text.toString())
                intent.putExtra("image1", photo1.toString())
                intent.putExtra("image2", photo2.toString())
                intent.putExtra("image3", photo3.toString())
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
    }

    override fun setData() {  // 수정시 저장되어있던 데이터들을 빈칸에 입력해주는 코드
        if (intent.hasExtra("status")) {
            actionBar?.title = "실종/보호 신고 수정"
            modifyId = intent.getIntExtra("id", -1)
            Log.e("주제수정 id", modifyId.toString())
            missing_info_status.text = intent.getStringExtra("status")

            // datetime 에서 date 만 뽑아와서 출력
            val outputFormat = SimpleDateFormat("yyyy-MM-dd")
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S")

            val inputText = intent.getStringExtra("date")
            val date = inputFormat.parse(inputText)
            val outputText = outputFormat.format(date)
            missing_info_date.text = outputText

            missing_info_city.text = intent.getStringExtra("city")
            missing_info_district.text = intent.getStringExtra("district")
            missing_info_detail_location.setText(intent.getStringExtra("detail_location"))
            missing_info_phone.setText(intent.getStringExtra("phone"))
            missing_info_species.text = intent.getStringExtra("species")
            missing_info_breed.text = intent.getStringExtra("breed")
            missing_info_gender.text = intent.getStringExtra("gender")
            if (intent.getBooleanExtra("neuter", false)) {
                missing_info_neuter.isChecked = true
                Log.e("tr", "")
            } else {
                Log.e("fa", "")
            }
            missing_info_age.text = intent.getStringExtra("age")
            missing_info_weight.text = intent.getStringExtra("weight")
            missing_info_pattern.setText(intent.getStringExtra("pattern"))
            missing_info_feature.setText(intent.getStringExtra("feature"))
            missing_info_etc.setText(intent.getStringExtra("etc"))
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
        val builder = AlertDialog.Builder(this@MissingReportActivity)
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

    override fun albumClick() { // 앨범 선택
        val intentAlbum = Intent(Intent.ACTION_PICK)

        /*       tempFile = missingReportPresenter.createImageFile()
               try {

               } catch (e: IOException) {
                   Toast.makeText(this, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                   finish()
                   e.printStackTrace()
               }
               if (tempFile != null) {
                   val photoUri =
                       FileProvider.getUriForFile(this, "com.example.saveme.fileprovider", tempFile)
                   Log.e("앨범에서 가져온 photoUri", photoUri.toString())
               }*/
        intentAlbum.type = MediaStore.Images.Media.CONTENT_TYPE
        startActivityForResult(intentAlbum, PICK_FROM_ALBUM)
    }

    override fun cameraClick() { // 카메라 선택
        val intentCamera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        try {
            tempFile = missingReportPresenter.createImageFile()
        } catch (e: IOException) {
            Toast.makeText(this, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
            finish()
            e.printStackTrace()
        }

        if (tempFile != null) {
            /*val photoUri =
                FileProvider.getUriForFile(this, "com.example.saveme.provider", tempFile)*/

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {

                // 안드로이드 누가 하위버전에서는 provider로 uri를 감싸주면 동작하지 않는 경우가 있기 때문에 버전 구분 필요

                if (tempFile != null) {

                }
                    val photoUri =
                        FileProvider.getUriForFile(this, "com.example.saveme.provider", tempFile!!)
                    intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                    startActivityForResult(intentCamera, PICK_FROM_CAMERA)
                } else {

                    val photoUri = Uri.fromFile(tempFile)
                    intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                    startActivityForResult(intentCamera, PICK_FROM_CAMERA)
                }
            }
        }

        private fun cropImage(photoUri: Uri) {
            Log.e("tempFile : ", tempFile.toString())
            // 갤러리에서 가져온 사진을 크롭화면을 보낸다
            // 갤러리에서 선택한 경우에는 tempFile 이 없으므로 새로 생성해준다
            if (tempFile == null) {
                try {
                    tempFile = missingReportPresenter.createImageFile()
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
                val options = BitmapFactory.Options()
                val originalBm = BitmapFactory.decodeFile(tempFile!!.absolutePath, options)
                val resizedBitmap =
                    Bitmap.createScaledBitmap(originalBm, 100, 100, true) // 이미지 사이즈 조정
                Log.e("setImage", "" + tempFile!!.absolutePath)

                missing_info_photo1.setImageBitmap(resizedBitmap) // 이미지뷰에 조정한 이미지 넣기

                /*
                tempFile 사용 후 null 처리를 해줘야 한다.
                (resultCode != RESULT_OK) 일 때 tempFile 을 삭제하기 때문에
                기존에 데이터가 남아 있게 되면 원치 않은 삭제가 이뤄진다.
                */

                tempFile = null
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

                    setImage()
                }
                PICK_FROM_CAMERA -> {  // 카메라에서 온 경우
                    Log.e("PICK_FROM_CAMERA", "카메라 선택후")
                    val bitmap =
                        MediaStore.Images.Media.getBitmap(contentResolver, Uri.fromFile(tempFile))
                    val ei = ExifInterface(tempFile?.absolutePath)
                    val orientation = ei.getAttributeInt(
                        ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_UNDEFINED
                    )
                    val rotatedBitmap = missingReportPresenter.rotateImage(bitmap, 90.toFloat())
                    //            storeImage(rotatedBitmap!!)

                    setImage()
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
