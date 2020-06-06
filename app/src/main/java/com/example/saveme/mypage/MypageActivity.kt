package com.example.saveme.mypage

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import com.example.saveme.R
import com.example.saveme.base.BaseActivity
import com.example.saveme.community.CommunityActivity
import com.example.saveme.home.HomeActivity
import com.example.saveme.login.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.bnv_home
import kotlinx.android.synthetic.main.activity_mypage.*

class MypageActivity : BaseActivity(), MypageContract.View {

    private lateinit var mypagePresenter: MypagePresenter
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)

        mypagePresenter.takeView(this)
        bottomNavigationView()
        mypagePresenter.getUser(this)

        textView12.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        btn_logout.setOnClickListener {
            mypagePresenter.logout(this)
        }

    }

    override fun initPresenter() {
        mypagePresenter = MypagePresenter()
    }

    override fun bottomNavigationView() {
        bnv_home.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bottom_home -> {
                    val intent_home = Intent(this, HomeActivity::class.java)
                    intent_home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    overridePendingTransition(0, 0)
                    startActivity(intent_home)
                    finish()
                }
                R.id.bottom_community -> {
                    val intent_community = Intent(this, CommunityActivity::class.java)
                    intent_community.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    overridePendingTransition(0, 0)
                    startActivity(intent_community)
                    finish()
                }
                R.id.bottom_mypage -> {

                }
            }
            false
        }
        bnv_home.menu.findItem(R.id.bottom_mypage)?.isChecked = true
    }

    // 사용자 정보를 view에 넣어주는 함수
    override fun setUserData(username: String, userEmail: String) {
        mypage_username.text = username

    }

    // 로그아웃 시 preference에 저장되어있는 token 삭제 -> LoginActivity열기
    override fun logout() {
        preferences = this.getSharedPreferences("USERSIGN", 0)

        val editor = preferences.edit()
        editor.putString("Cookie", "")
        editor.commit()

        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    override fun showError(error: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showToastMessage(msg: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
