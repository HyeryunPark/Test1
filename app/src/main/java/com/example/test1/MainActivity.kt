package com.example.test1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.test1.community.CommunityActivity
import com.example.test1.missing.MissingActivity
import com.example.test1.shelter.ShelterActivity
import com.example.test1.login.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        mypage.setOnClickListener {
            var intent_mypage = Intent(this, LoginActivity::class.java)
            startActivity(intent_mypage)
        }

        community.setOnClickListener {
            var intent_community = Intent(this, CommunityActivity::class.java)
            startActivity(intent_community)
        }

    }
}
