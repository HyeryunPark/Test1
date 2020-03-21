package com.example.saveme

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        // GET (글 읽어오기)
        btn1.setOnClickListener {

        }

        // POST(글 작성하기)
        btn2.setOnClickListener {

        }

        // PATCH(글 수정하기)
        btn3.setOnClickListener {

        }

        // DELETE(글 삭제하기)
        btn4.setOnClickListener {

        }

    }

}