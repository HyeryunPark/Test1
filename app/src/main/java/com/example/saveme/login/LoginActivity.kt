package com.example.saveme.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.saveme.home.HomeActivity
import com.example.saveme.signup.SignUpActivity
import com.example.saveme.R
import com.example.saveme.base.BaseActivity
import com.example.saveme.mypage.MypageActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), LoginContract.View {


    private lateinit var loginPresenter: LoginPresenter     // LoginActivity와 1:1 대응하는 LoginPresenter를 연결시켜주기 위한 초기화 지연변수

//    private var lastTimeBackPressed: Long = -1500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // 회원가입 하러가는 버튼
        btn_join.setOnClickListener {
            var intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        loginPresenter.takeView(this)   // LoginContract.View를 상속받는 Activity가 생성이 되었다는 것을 Presenter에 알려준다.

        pressedLoginBtn()


    }

    override fun initPresenter() {          // BaseActivity에서 Activity가 생성이되면 해당 Activity에 Presenter를 초기화 시켜준다.
        loginPresenter = LoginPresenter()
    }

    // LoginActivity에서 로그인버튼을 누름
    private fun pressedLoginBtn() {
        btn_login.setOnClickListener {
            loginPresenter.logIn(this, et_email.text.toString(), et_pw.text.toString())
        }
    }

    override fun showToastMessage(msg: String) {
        Toast.makeText(this@LoginActivity, msg, Toast.LENGTH_SHORT).show()
    }

    override fun startMainActivity() {
        var intent = Intent(this, MypageActivity::class.java)
        startActivity(intent)
        finish()
    }

/*    override fun onBackPressed() {
//        super.onBackPressed()

        // ( 나중에 버튼 누른 시간 - 이전에 버튼 누른 시간 ) <= 1.5초
        if (System.currentTimeMillis() - lastTimeBackPressed <= 1500)
            finish()
        lastTimeBackPressed = System.currentTimeMillis()
        Toast.makeText(this, "한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()

    }*/

    override fun onDestroy() {
        super.onDestroy()
        loginPresenter.dropView()
    }

    override fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        loginRefresh.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        loginRefresh.visibility = View.GONE
    }


}
