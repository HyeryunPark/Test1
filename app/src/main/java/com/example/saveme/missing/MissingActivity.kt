package com.example.saveme.missing

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.example.saveme.R
import com.example.saveme.base.BaseActivity
import com.example.saveme.missing.createmissing.MissingReportActivity
import com.example.saveme.missing.missingdetail.MissingDetailActivity
import com.example.saveme.network.RetrofitClient
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_missing.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MissingActivity : BaseActivity(), MissingContract.View {

    private lateinit var missingPresenter: MissingPresenter
    var missingList = arrayListOf<MissingModel>()
    lateinit var missingAdapter: MissingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_missing)

        // 툴바선언
        setSupportActionBar(toolbar_missing)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)  // 왼쪽버튼 사용 여부 true
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_white_24dp)   // 왼쪽버튼 이미지 설정
        supportActionBar!!.setDisplayShowTitleEnabled(false)    // 기본 타이틀 사용 여부 false

        missingPresenter.takeView(this)

        missingAdapter = MissingAdapter(this, missingList, missingPresenter)
        missingPresenter.loadItems(missingAdapter, missingList, this)

        // 툴바 타이틀 누르면 실종 동물 상세볼수 있음. (발표 후에 삭제할 것)
        toolbar_title.setOnClickListener {
            var intent_toolbarTitle = Intent(this, MissingDetailActivity::class.java)
            startActivity(intent_toolbarTitle)
        }

        // 플로팅버튼 누르면 실종/보호 동물 글쓰기 가능
        fab_missing_write.setOnClickListener {
            var intent_write = Intent(this, MissingReportActivity::class.java)
//            startActivity(intent_write)
            startActivityForResult(intent_write, 101)
        }


        // Recyclerview
        rv_missing.adapter = missingAdapter
        rv_missing.layoutManager = GridLayoutManager(this, 2)
        rv_missing.setHasFixedSize(true)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            101 -> {
                when (resultCode) {
                    Activity.RESULT_OK -> if (data != null) {
                        missingPresenter.addItems(
                            data.getStringExtra("status"),
                            data.getStringExtra("date"),
                            data.getStringExtra("city"),
                            data.getStringExtra("district"),
                            data.getStringExtra("detail_location"),
                            data.getStringExtra("phone"),
                            data.getStringExtra("species"),
                            data.getStringExtra("breed"),
                            data.getStringExtra("gender"),
                            data.getBooleanExtra("neuter", false),
                            data.getStringExtra("age"),
                            data.getStringExtra("weight"),
                            data.getStringExtra("pattern"),
                            data.getStringExtra("feature"),
                            data.getStringExtra("etc"),
                            this,
                            missingAdapter,
                            missingList
                        )

                    }
                }
            }
        }

    }

    override fun initPresenter() {
        missingPresenter = MissingPresenter()
    }

    override fun refresh() {
        missingAdapter.notifyDataSetChanged()
    }

    override fun showError(error: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showToastMessage(msg: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
