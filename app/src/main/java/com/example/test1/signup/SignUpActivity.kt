package com.example.test1.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.test1.R
import com.example.test1.base.BaseActivity
import com.example.test1.login.LoginActivity
import com.example.test1.model.User
import kotlinx.android.synthetic.main.activity_join.*

class SignUpActivity : BaseActivity(), SignUpContract.View {

    private lateinit var signUpPresenter: SignUpPresenter   // SignUpActivity 1:1 대응하는 SignUpPresenter 연결시켜주기 위한 초기화 지연변수

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        signUpPresenter.takeView(this)

        pressedSignUpBtn()
    }
    override fun initPresenter() {  // BaseActivity에서 Activity가 생성이되면 해당 Activity에 Presenter를 초기화 시켜준다.
        signUpPresenter = SignUpPresenter()
    }

    override fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    override fun showToastMessage(msg: String) {
        Toast.makeText(this@SignUpActivity, msg, Toast.LENGTH_SHORT).show()
    }

    override fun startLoginActivity() {
        var intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()

    }

    override fun onDestroy() {
        super.onDestroy()
        signUpPresenter.dropView()
    }

    private fun pressedSignUpBtn(){
        btn_joinOK.setOnClickListener {
            var userData = User(
                et_signUpEmail.text.toString(),
                et_signUpName.text.toString(),
                et_signUpPw.text.toString()
            )
            signUpPresenter.insertUserData(userData)
        }
    }


}
