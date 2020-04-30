package com.example.saveme.mypage

import android.content.Intent
import android.os.Bundle
import com.example.saveme.R
import com.example.saveme.base.BaseActivity
import com.example.saveme.community.CommunityActivity
import com.example.saveme.home.HomeActivity
import kotlinx.android.synthetic.main.activity_main.*

class MypageActivity : BaseActivity(), MypageContract.View {

    private lateinit var mypagePresenter: MypagePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)

        mypagePresenter.takeView(this)
        bottomNavigationView()

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

    override fun showError(error: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showToastMessage(msg: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
