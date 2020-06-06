package com.example.saveme.community

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.saveme.R
import com.example.saveme.base.BaseActivity
import com.example.saveme.community.createcommunity.CommunityReportActivity
import com.example.saveme.home.HomeActivity
import com.example.saveme.login.LoginActivity
import com.example.saveme.model.CreateCommunity
import com.example.saveme.mypage.MypageActivity
import kotlinx.android.synthetic.main.activity_community.*

class CommunityActivity : BaseActivity(), CommunityContract.View {

    private lateinit var communityPresenter: CommunityPresenter
    var communityList = arrayListOf<CommunityModel>()
    private lateinit var preferences: SharedPreferences

    lateinit var communityAdapter: CommunityAdapter

    private var lastTimeBackPressed: Long = -1500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community)

        communityPresenter.takeView(this)

        communityAdapter = CommunityAdapter(this, communityList, communityPresenter)
        communityPresenter.loadItems(communityAdapter, communityList, this)

        bottomNavigationView()

        // RecyclerView
        rv_community.adapter = communityAdapter
        rv_community.layoutManager = LinearLayoutManager(this)

        // 플러팅버튼 누르면 글 작성하는 페이지로 이동
        ftb_community_write.setOnClickListener {
            val intent = Intent(this, CommunityReportActivity::class.java)
            startActivityForResult(intent, 101)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            101 -> {  // 글 작성 후
                when (resultCode) {
                    Activity.RESULT_OK -> if (data != null) {

                        val createCommunity: CreateCommunity = CreateCommunity(
                            data.getIntExtra("user_id", 0),
                            data.getStringExtra("community_category"),
                            data.getStringExtra("community_title"),
                            data.getStringExtra("community_content"),
                            data.getStringExtra("img1"),
                            data.getStringExtra("img2"),
                            data.getStringExtra("img3")
                        )

                        communityPresenter.addItems(
                            createCommunity,
                            this,
                            communityAdapter,
                            communityList
                        )
                    }
                }
            }

        }
    }

    override fun initPresenter() {
        communityPresenter = CommunityPresenter()
    }

    override fun refresh() {
        communityAdapter.notifyDataSetChanged()
    }

    override fun bottomNavigationView() {
        bnv_community.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bottom_home -> {
                    val intent_home = Intent(this, HomeActivity::class.java)
                    intent_home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    overridePendingTransition(0, 0)
                    startActivity(intent_home)
                    finish()
                }
                R.id.bottom_community -> {

                }
                R.id.bottom_mypage -> {
                    preferences = this.getSharedPreferences("USERSIGN", 0)
                    if (preferences.getString("Cookie", "") == "") {
                        val intent_login = Intent(this, LoginActivity::class.java)
                        startActivity(intent_login)
                    } else {
                        val intent_mypage = Intent(this, MypageActivity::class.java)
                        intent_mypage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        overridePendingTransition(0, 0)
                        startActivity(intent_mypage)
                        finish()
                    }
                }
            }
            false
        }
        bnv_community.menu.findItem(R.id.bottom_community)?.isChecked = true

    }

    override fun onBackPressed() {
//        super.onBackPressed()

        // ( 나중에 버튼 누른 시간 - 이전에 버튼 누른 시간 ) <= 1.5초
        if (System.currentTimeMillis() - lastTimeBackPressed <= 1500)
            finish()
        lastTimeBackPressed = System.currentTimeMillis()
        Toast.makeText(this, "한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()

    }

    override fun showError(error: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showToastMessage(msg: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
