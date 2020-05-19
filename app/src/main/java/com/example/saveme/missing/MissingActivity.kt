package com.example.saveme.missing

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.saveme.R
import com.example.saveme.base.BaseActivity
import com.example.saveme.missing.createmissing.MissingReportActivity
import com.example.saveme.model.CreateMissing
import kotlinx.android.synthetic.main.activity_missing.*

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
            101 -> {    // 글 작성하고 돌아왔을 때
                when (resultCode) {
                    Activity.RESULT_OK -> if (data != null) {

                        val createMissing: CreateMissing = CreateMissing(
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
                            data.getStringExtra("image1"),
                            data.getStringExtra("image2"),
                            data.getStringExtra("image3")
                        )

                        missingPresenter.addItems(createMissing, this, missingAdapter, missingList)

                    }
                }
            }
            102 -> {    // 글 수정하고 돌아왔을 때
                when (resultCode) {
                    Activity.RESULT_OK -> if (data != null) {
                        Log.e("수정화면에서 돌아왔을때 id", data.getIntExtra("id", -1).toString())

                        val missingModel: MissingModel = MissingModel(
                            data.getIntExtra("id", -1),
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
                            data.getStringExtra("image1"),
                            data.getStringExtra("image2"),
                            data.getStringExtra("image3")
                        )

                        missingPresenter.updateItems(
                            data.getIntExtra("id", -1),
                            missingModel,
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

    override fun modifyActivity(id: Int, missingModel: MissingModel) { // 글 수정화면과 처음 작성하는 화면 공유
        val intent = Intent(this, MissingReportActivity::class.java)
        intent.putExtra("id", id)
        intent.putExtra("status", missingModel.status)
        intent.putExtra("date", missingModel.date)
        intent.putExtra("city", missingModel.city)
        intent.putExtra("district", missingModel.district)
        intent.putExtra("detail_location", missingModel.detailLocation)
        intent.putExtra("phone", missingModel.phone)
        intent.putExtra("species", missingModel.species)
        intent.putExtra("breed", missingModel.breed)
        intent.putExtra("gender", missingModel.gender)
        intent.putExtra("neuter", missingModel.neuter)
        intent.putExtra("age", missingModel.age)
        intent.putExtra("weight", missingModel.weight)
        intent.putExtra("pattern", missingModel.pattern)
        intent.putExtra("feature", missingModel.feature)
        intent.putExtra("etc", missingModel.etc)
        intent.putExtra("image1", missingModel.image1)
        intent.putExtra("image2", missingModel.image2)
        intent.putExtra("image3", missingModel.image3)
        startActivityForResult(intent, 102)
    }

    override fun showNothingText(type: Boolean) {
        if (type) { // type = true (리스트가 비어있을 때)
            tv_missing_nothingText.visibility = View.VISIBLE
            rv_missing.visibility = View.GONE
        } else {
            tv_missing_nothingText.visibility = View.GONE
            rv_missing.visibility = View.VISIBLE
        }
    }

    override fun showError(error: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showToastMessage(msg: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
