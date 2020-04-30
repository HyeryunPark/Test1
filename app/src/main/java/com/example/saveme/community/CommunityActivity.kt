package com.example.saveme.community

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.saveme.R
import com.example.saveme.base.BaseActivity
import com.example.saveme.home.HomeActivity
import com.example.saveme.login.LoginActivity
import com.example.saveme.mypage.MypageActivity
import kotlinx.android.synthetic.main.activity_community.*
import kotlinx.android.synthetic.main.activity_main.*

class CommunityActivity : BaseActivity(), CommunityContract.View {

    private lateinit var communityPresenter: CommunityPresenter

    private var lastTimeBackPressed: Long = -1500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community)

        communityPresenter.takeView(this)

        bottomNavigationView()

        // RecyclerView
        rv_community.adapter = CommunityAdapter()
        rv_community.layoutManager = LinearLayoutManager(this)
    }

    override fun initPresenter() {
        communityPresenter = CommunityPresenter()
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
                    val intent_mypage = Intent(this, MypageActivity::class.java)
                    intent_mypage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    overridePendingTransition(0, 0)
                    startActivity(intent_mypage)
                    finish()
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
