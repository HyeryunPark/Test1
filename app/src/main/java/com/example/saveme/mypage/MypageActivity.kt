package com.example.saveme.mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.saveme.R
import com.example.saveme.base.BaseActivity

class MypageActivity : BaseActivity(),  MypageContract.View {

    private lateinit var mypagePresenter: MypagePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)
    }

    override fun initPresenter() {
        mypagePresenter = MypagePresenter()
    }

    override fun showError(error: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showToastMessage(msg: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
