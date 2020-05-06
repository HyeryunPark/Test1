package com.example.saveme.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.saveme.R
import com.example.saveme.base.BaseActivity
import com.example.saveme.base.BaseView
import com.example.saveme.community.CommunityActivity
import com.example.saveme.missing.MissingActivity
import com.example.saveme.shelter.ShelterActivity
import com.example.saveme.login.LoginActivity
import com.example.saveme.mypage.MypageActivity
import kotlinx.android.synthetic.main.activity_main.*

class HomeActivity : BaseActivity(), HomeContract.View {

    private lateinit var homePresenter: HomePresenter

    private var lastTimeBackPressed: Long = -1500

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        homePresenter.takeView(this)

        bottomNavigationView()

        // 보호소 버튼
        btn_shelter.setOnClickListener {

            var intent_shelter = Intent(this, ShelterActivity::class.java)
            startActivity(intent_shelter)

        }

        // 실종,보호 버튼
        btn_missing.setOnClickListener {

            var intent_missing = Intent(this, MissingActivity::class.java)
            startActivity(intent_missing)

        }

    }

    override fun initPresenter() {
        homePresenter = HomePresenter()
    }

    override fun bottomNavigationView() {
        bnv_home.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bottom_home -> {

                }
                R.id.bottom_community -> {
                    val intent_community = Intent(this, CommunityActivity::class.java)
                    intent_community.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    overridePendingTransition(0, 0)
                    startActivity(intent_community)
                    finish()
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
        bnv_home.menu.findItem(R.id.bottom_home)?.isChecked = true

    }

    override fun onBackPressed() {
//        super.onBackPressed()

        // ( 나중에 버튼 누른 시간 - 이전에 버튼 누른 시간 ) <= 1.5초
        if (System.currentTimeMillis() - lastTimeBackPressed <= 1500)
            finish()
        lastTimeBackPressed = System.currentTimeMillis()
        Toast.makeText(this, "한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()

    }

    override fun onDestroy() {
        super.onDestroy()

        homePresenter.dropView()
    }

    override fun showError(error: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showToastMessage(msg: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
