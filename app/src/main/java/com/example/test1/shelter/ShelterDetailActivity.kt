package com.example.test1.shelter

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.test1.R
import kotlinx.android.synthetic.main.activity_shelter_detail.*

class ShelterDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shelter_detail)

        // 툴바선언
        setSupportActionBar(toolbar_shelter_detail)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)  // 왼쪽버튼 사용 여부 true
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_pink_24dp)   // 왼쪽버튼 이미지 설정
        supportActionBar!!.setDisplayShowTitleEnabled(false)    // 기본 타이틀 사용 여부 false

        val detail_intent = intent
        val img = detail_intent.getStringExtra("img")
        val kind = detail_intent.getStringExtra("kind")
        val gender = detail_intent.getStringExtra("gender")
        val neuter = detail_intent.getStringExtra("neuter")
        val pattern = detail_intent.getStringExtra("pattern")
        val age = detail_intent.getStringExtra("age")
        val weight = detail_intent.getStringExtra("weight")
        val notice_number = detail_intent.getStringExtra("notice_number")
        val date = detail_intent.getStringExtra("date")
        val area = detail_intent.getStringExtra("area")
        val spot = detail_intent.getStringExtra("spot")
        val feature = detail_intent.getStringExtra("feature")
        val protection_center = detail_intent.getStringExtra("protection_center")
        val department_in_charge = detail_intent.getStringExtra("department_in_charge")
        val protection_center_phone = detail_intent.getStringExtra("protection_center_phone")
        val protection_center_address = detail_intent.getStringExtra("protection_center_address")

        Glide.with(this).load(img).fitCenter().into(iv_shelter_detail_image)
        tv_shelter_detail_kind.text = kind

        if (gender == "f") {
            tv_shelter_detail_gender.text = "암컷"
        } else if (gender == "m") {
            tv_shelter_detail_gender.text = "수컷"
        }

        if (neuter == "f") {
            tv_shelter_detail_neuter.text = "(중성화 X)"
        } else if (neuter == "t") {
            tv_shelter_detail_neuter.text = "(중성화 O)"
        } else {
            tv_shelter_detail_neuter.text = "(중성화 모름)"
        }

        tv_shelter_detail_pattern.text = "/ " + pattern
        tv_shelter_detail_age.text = "/ " + age
        tv_shelter_detail_weight.text = "/ " + weight
        tv_shelter_detail_notice_number.text = notice_number
        tv_shelter_detail_notice_period.text = date
        tv_shelter_detail_spot_of_discovery.text = area + "" + spot
        tv_shelter_detail_feature.text = feature
        tv_shelter_detail_protection_center.text = protection_center
        tv_shelter_detail_department.text = department_in_charge


        btn_adoption_enquiry.setOnClickListener {
            val positiveButtonClick = { dialog: DialogInterface, which: Int ->
                val intent = Intent(Intent.ACTION_DIAL)
                val TEST_DIAL_NUMBER = Uri.fromParts("tel", protection_center_phone, null)
                intent.setData(TEST_DIAL_NUMBER)
                startActivity(intent)
            }


            val builder = AlertDialog.Builder(this)

            /*   with(builder)
               {
                   setTitle("입양문의")
                   setMessage("보호소로 입양문의 하시겠습니까?")
                   setPositiveButton("네", DialogInterface.OnClickListener(function = positiveButtonClick)
                   )
                   setNegativeButton("아니오") { dialog, which ->
                       dialog.dismiss()
                   }
                   show()
               }*/
            builder.setTitle("입양문의")
                .setMessage("보호소로 입양문의 하시겠습니까?")
                .setPositiveButton("네") { dialogInterface, i ->
                    val intent = Intent(Intent.ACTION_DIAL)
                    val TEST_DIAL_NUMBER = Uri.fromParts("tel", protection_center_phone, null)
                    intent.setData(TEST_DIAL_NUMBER)
                    startActivity(intent)
                }
                .setNegativeButton("아니오") { dialogInterface, i ->
                    dialogInterface.dismiss()
                }
                .show()
        }

        btn_adoption_method.setOnClickListener {
            val url = "http://www.animal.go.kr/portal_rnl/abandonment/adoption_info.jsp"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)


        }


        btn_show_shelter.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this).create()
            alertDialog.setTitle("보호소 위치")
            alertDialog.setMessage(protection_center_address)

            alertDialog.setButton(
                AlertDialog.BUTTON_NEGATIVE, "닫기"
            ) { dialog, which -> dialog.dismiss() }
            alertDialog.show()

            val btnNegative = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)

            val layoutParams = btnNegative.layoutParams as LinearLayout.LayoutParams
            layoutParams.weight = 10f
            btnNegative.layoutParams = layoutParams
        }
    }

    // 툴바 메뉴
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.shelter_menu, menu)
        return true
    }


}
