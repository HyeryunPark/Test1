package com.example.saveme.missing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.example.saveme.R
import com.example.saveme.model.MissingModel
import com.example.saveme.network.RetrofitClient
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_missing.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MissingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_missing)

        // 툴바선언
        setSupportActionBar(toolbar_missing)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)  // 왼쪽버튼 사용 여부 true
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_white_24dp)   // 왼쪽버튼 이미지 설정
        supportActionBar!!.setDisplayShowTitleEnabled(false)    // 기본 타이틀 사용 여부 false

        // 툴바 타이틀 누르면 실종 동물 상세볼수 있음. (발표 후에 삭제할 것)
        toolbar_title.setOnClickListener {
            var intent_toolbarTitle = Intent(this, MissingDetailActivity::class.java)
            startActivity(intent_toolbarTitle)
        }

        // 플로팅버튼 누르면 실종/보호 동물 글쓰기 가능
        fab_missing_write.setOnClickListener {
            var intent_write = Intent(this, MissingReportActivity::class.java)
            startActivity(intent_write)
        }


        // Recyclerview
//        rv_missing.adapter = MissingAdapter(this,)
        rv_missing.layoutManager = GridLayoutManager(this, 2)
        rv_missing.adapter?.notifyDataSetChanged()

        // retrofit
        val retrofitInterface = RetrofitClient.retrofitInterface
        retrofitInterface.requestMissingData().enqueue(object : Callback<List<MissingModel>>{

            override fun onResponse(call: Call<List<MissingModel>>, response: Response<List<MissingModel>>) {
                if(response.isSuccessful){
                    Log.e("Success", Gson().toJson(response.body()))

                    val body = response.body()
                    body?.let {
                        Log.e("잘들어옴",".")
                        rv_missing.adapter = MissingAdapter(this@MissingActivity, response?.body()!!)
                    }
                }
            }
            override fun onFailure(call: Call<List<MissingModel>>, t: Throwable) {
                Log.e("실종동물 정보 받아오기 실패",t.toString())
            }

        })




    }


}
