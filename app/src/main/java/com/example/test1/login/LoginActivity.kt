package com.example.test1.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.test1.MainActivity
import com.example.test1.signup.SignUpActivity
import com.example.test1.R
import com.example.test1.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity :  BaseActivity(), LoginContract.View {


    private lateinit var loginPresenter: LoginPresenter     // LoginActivity와 1:1 대응하는 LoginPresenter를 연결시켜주기 위한 초기화 지연변수

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // 회원가입 하러가는 버튼
        btn_join.setOnClickListener {
            var intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        loginPresenter.takeView(this)   // LoginContract.View를 상속받는 Activity가 생성이 되었다는 것을 Presenter에 알려준다.

        setButton()     // 버튼 이벤트가 발생하면 Presenter에 이벤트가 발생하였다고 알려줌과 동시에 Model로 부터 데이터를 가져오라는 것을 알려준다.

        pressedLoginBtn()


    }

    private fun setButton() {
        btn_loginKakao.setOnClickListener {
            loginPresenter.getUserList()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        loginPresenter.dropView()
    }


    override fun initPresenter() {          // BaseActivity에서 Activity가 생성이되면 해당 Activity에 Presenter를 초기화 시켜준다.
        loginPresenter = LoginPresenter()
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

    //-------------------------------------------------------------------------------------------------------------------

    // LoginActivity에서 로그인버튼을 누름
    private fun pressedLoginBtn() {
        btn_login.setOnClickListener {
            loginPresenter.checkLoginUser(et_email.text.toString(),et_pw.text.toString())
        }
    }

    override fun showToastMessage(msg: String){
        Toast.makeText(this@LoginActivity, msg, Toast.LENGTH_SHORT).show()
    }


    override fun startMainActivity() {
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
