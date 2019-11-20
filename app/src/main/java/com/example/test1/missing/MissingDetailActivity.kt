package com.example.test1.missing

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.test1.R
import com.example.test1.model.MissingModel
import com.example.test1.network.RetrofitClient
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_missing_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MissingDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_missing_detail)

        // 툴바선언
        setSupportActionBar(toolbar_missing_detail)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)  // 왼쪽버튼 사용 여부 true
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_pink_24dp)   // 왼쪽버튼 이미지 설정
        supportActionBar!!.setDisplayShowTitleEnabled(false)    // 기본 타이틀 사용 여부 false


        val detail_intent = intent
        val status = detail_intent.getStringExtra("status")
        val species = detail_intent.getStringExtra("species")
        val breed = detail_intent.getStringExtra("breed")
        val gender = detail_intent.getStringExtra("gender")
        val neuter = detail_intent.getStringExtra("neuter")
        val age = detail_intent.getStringExtra("age")
        val weight = detail_intent.getStringExtra("weight")
        val pattern = detail_intent.getStringExtra("pattern")
        val feature = detail_intent.getStringExtra("feature")
        val phone = detail_intent.getStringExtra("phone")
        val etc = detail_intent.getStringExtra("etc")
        val date = detail_intent.getStringExtra("date")
        val detail_location = detail_intent.getStringExtra("detail_location")
        val city = detail_intent.getStringExtra("city")
        val district = detail_intent.getStringExtra("district")

        tv_missing_detail_status.text = status
        if (status == "실종")
            tv_missing_detail_status.setBackgroundColor(Color.parseColor("#f77345"))
        else if (status == "목격")
            tv_missing_detail_status.setBackgroundColor(Color.parseColor("#e3d21b"))
        else if (status == "보호")
            tv_missing_detail_status.setBackgroundColor(Color.parseColor("#66CC00"))
        else if (status == "완료")
            tv_missing_detail_status.setBackgroundColor(Color.parseColor("#0033CC"))


        tv_missing_detail_species.text = "[" + species + "]"
        tv_missing_detail_breed.text = breed
        tv_missing_detail_gender.text = gender
        if (neuter == "true") {
            tv_missing_detail_neuter.text = " (중성화 o)"
        } else {
            tv_missing_detail_neuter.text = " (중성화 x)"
        }
        tv_missing_detail_age.text = "/ " + age

        if (weight == "모름") {
            tv_missing_detail_weight.text = "/ " + weight
        } else
            tv_missing_detail_weight.text = "/ " + weight + "kg"

        tv_missing_detail_pattern.text = "/ " + pattern
        tv_missing_detail_feature.text = feature
//        tv_missing_detail_user_phone.text = phone
        tv_missing_detail_user_phone.text =
            PhoneNumberUtils.formatNumber(phone, Locale.getDefault().country)
        tv_missing_detail_etc.text = etc
        tv_missing_detail_date.text = date
        tv_missing_detail_location.text = city + district + detail_location
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.missing_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item!!.itemId) {
            R.id.modify -> {  // 수정버튼

                Toast.makeText(this,"수정",Toast.LENGTH_SHORT).show()
            }
            R.id.delete -> {  // 삭제버튼
                deleteMissingData()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteMissingData() {
        // 실종동물 글을 삭제하는 코드
        // retrofit
        val retrofitInterface = RetrofitClient.retrofitInterface
        retrofitInterface.deleteMissingData(5).enqueue(object :Callback<MissingModel>{
            override fun onResponse(call: Call<MissingModel>, response: Response<MissingModel>) {
                if(response.isSuccessful){
                    Log.e("Success", Gson().toJson(response.body()))
                    finish()
                    Toast.makeText(this@MissingDetailActivity,"삭제완료",Toast.LENGTH_SHORT).show()

                }
                else
                    Log.e("unSuccess", Gson().toJson(response.errorBody()))

            }

            override fun onFailure(call: Call<MissingModel>, t: Throwable) {
                Log.e("글 삭제 실패",t.toString())

            }

        })

    }


}
