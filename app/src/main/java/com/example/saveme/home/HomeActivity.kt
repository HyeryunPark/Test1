package com.example.saveme.home

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.saveme.R
import com.example.saveme.base.BaseActivity
import com.example.saveme.base.BaseView
import com.example.saveme.community.CommunityActivity
import com.example.saveme.community.CommunityModel
import com.example.saveme.missing.MissingActivity
import com.example.saveme.shelter.ShelterActivity
import com.example.saveme.login.LoginActivity
import com.example.saveme.mypage.MypageActivity
import kotlinx.android.synthetic.main.activity_main.*

class HomeActivity : BaseActivity(), HomeContract.View {

    private lateinit var homePresenter: HomePresenter
    var communityList = arrayListOf<CommunityModel>()
    private lateinit var preferences: SharedPreferences


    private var lastTimeBackPressed: Long = -1500

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        homePresenter.takeView(this)
        homePresenter.loadItems(communityList, this)

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
        bnv_home.menu.findItem(R.id.bottom_home)?.isChecked = true

    }

    // presenter 에서 얻어온 데이터를 화면에 반영
    override fun setAdoptItems1(title: String, img1: String) {
        Glide.with(this).load(img1).into(main_adopt_img1)
        main_adopt_tv1.text = title
    }

    override fun setAdoptItems2(title: String, img1: String) {
        Glide.with(this).load(img1).into(main_adopt_img2)
        main_adopt_tv2.text = title
    }

    override fun setAdoptItems3(title: String, img1: String) {
        Glide.with(this).load(img1).into(main_adopt_img3)
        main_adopt_tv3.text = title
    }

    override fun setProtectionItems1(title: String, img1: String) {
        Glide.with(this).load(img1).into(main_protection_img1)
        main_protection_tv1.text = title
    }

    override fun setProtectionItems2(title: String, img1: String) {
        Glide.with(this).load(img1).into(main_protection_img2)
        main_protection_tv2.text = title
    }

    override fun setProtectionItems3(title: String, img1: String) {
        Glide.with(this).load(img1).into(main_protection_img3)
        main_protection_tv3.text = title
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
