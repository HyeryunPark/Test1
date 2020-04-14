package com.example.saveme.shelter

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.saveme.R
import kotlinx.android.synthetic.main.activity_shelter.*
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.xml.sax.InputSource
import java.net.URL
import javax.xml.parsers.DocumentBuilderFactory

class ShelterActivity : AppCompatActivity() {

    private lateinit var shelterPresenter : ShelterPresenter
    private lateinit var openApiTask: OpenApiTask

    // 본인 인증키
    val serviceKey =
        "oH2mHVrDj%2BzIKdU4ZAuRqENqjNoOpdhCEYtcBD42m7rbpJ%2F2O5H3nFVoN1i7vycmeGnsTYWPKyhZHfdlRYUQEw%3D%3D"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shelter)

        // 툴바선언
        setSupportActionBar(toolbar_shelter)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)  // 왼쪽버튼 사용 여부 true
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_white_24dp)   // 왼쪽버튼 이미지 설정
        supportActionBar!!.setDisplayShowTitleEnabled(false)    // 기본 타이틀 사용 여부 false

        // Recyclerview
        rv_shelter.layoutManager = LinearLayoutManager(this)
        rv_shelter.setHasFixedSize(true)
        //  아이템이 추가되거나 삭제될 때 리싸이클로뷰의 크기가 변경될 수도 있고, 그렇게 되면 계층 구조의 다른 뷰의 크기가 변경될 가능성이 있다.
        //  특히 아이템 자주 추가/삭제 되면 오류가 날 수도 있기 때문에 setHasFixedSize(true)를 설정한다.

        openApiTask = OpenApiTask()
        openApiTask.execute()

    }

    private inner class OpenApiTask : AsyncTask<String, Void, Document>() {

        internal lateinit var doc: Document

        override fun doInBackground(vararg urls: String): Document {
            val url: URL

            try {
                url =
                    URL("http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/abandonmentPublic?ServiceKey=$serviceKey")

                Log.e("xml 파싱", "1단계")
                val dbf = DocumentBuilderFactory.newInstance()
                val db = dbf.newDocumentBuilder()
                doc = db.parse(InputSource(url.openStream()))
                doc.documentElement.normalize()
                Log.e("xml 파싱", "2단계")

            } catch (e: Exception) {
                Log.e("파싱에러", e.toString())
            }
            return doc
        }

        override fun onPostExecute(doc: Document) {

            val xmlList = mutableListOf<ShelterModel>() // 파싱한 xml데이터를 list에 집어넣는다.
            val nodeList = doc.getElementsByTagName("item")

            Log.e("xml 파싱", "파싱할 리스트 수 : " + nodeList.length)

            for (i in 0 until nodeList.length) {

                val node = nodeList.item(i)
                val fstElmnt = node as Element

                val age = fstElmnt.getElementsByTagName("age")

                val careAddr = fstElmnt.getElementsByTagName("careAddr")

                val careNm = fstElmnt.getElementsByTagName("careNm")

                val careTel = fstElmnt.getElementsByTagName("careTel")

                val chargeNm = fstElmnt.getElementsByTagName("chargeNm")

                val colorCd = fstElmnt.getElementsByTagName("colorCd")

                val desertionNo = fstElmnt.getElementsByTagName("desertionNo")

                val filename = fstElmnt.getElementsByTagName("filename")

                val happenDt = fstElmnt.getElementsByTagName("happenDt")

                val happenPlace = fstElmnt.getElementsByTagName("happenPlace")

                val kindCd = fstElmnt.getElementsByTagName("kindCd")

                val neuterYn = fstElmnt.getElementsByTagName("neuterYn")

                val noticeEdt = fstElmnt.getElementsByTagName("noticeEdt")

                val noticeNo = fstElmnt.getElementsByTagName("noticeNo")

                val noticeSdt = fstElmnt.getElementsByTagName("noticeSdt")

                val officetel = fstElmnt.getElementsByTagName("officetel")

                val orgNm = fstElmnt.getElementsByTagName("orgNm")

                val processState = fstElmnt.getElementsByTagName("processState")

                val sexCd = fstElmnt.getElementsByTagName("sexCd")

                val specialMark = fstElmnt.getElementsByTagName("specialMark")

                val weight = fstElmnt.getElementsByTagName("weight")


                var shelterModel: ShelterModel =
                    ShelterModel(
                        processState.item(0).childNodes.item(0).nodeValue,
                        filename.item(0).childNodes.item(0).nodeValue,
                        kindCd.item(0).childNodes.item(0).nodeValue,
                        happenDt.item(0).childNodes.item(0).nodeValue,
                        noticeNo.item(0).childNodes.item(0).nodeValue,
                        happenPlace.item(0).childNodes.item(0).nodeValue,
                        sexCd.item(0).childNodes.item(0).nodeValue,
                        neuterYn.item(0).childNodes.item(0).nodeValue,
                        colorCd.item(0).childNodes.item(0).nodeValue,
                        age.item(0).childNodes.item(0).nodeValue,
                        weight.item(0).childNodes.item(0).nodeValue,
                        desertionNo.item(0).childNodes.item(0).nodeValue,
                        specialMark.item(0).childNodes.item(0).nodeValue,
                        careNm.item(0).childNodes.item(0).nodeValue,
                        chargeNm.item(0).childNodes.item(0).nodeValue,
                        careTel.item(0).childNodes.item(0).nodeValue,
                        careAddr.item(0).childNodes.item(0).nodeValue
                    )
                xmlList.add(shelterModel)
            }
            Log.e("xml 파싱", "xmlList = $xmlList")

            rv_shelter.adapter = ShelterAdapter(this@ShelterActivity, xmlList)

            super.onPostExecute(doc)
        }
    }

    override fun onPause() {
        super.onPause()
        Log.i("MainActivity : ", "onPause()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("MainActivity : ", "onDestroy()")

        // 실행중인 AsyncTask의 상태를 얻어와서 RUNNING중이면 cancel 시켜버리겠다
        try {
            if (openApiTask.status == AsyncTask.Status.RUNNING)
                openApiTask.cancel(true)
        } catch (e: Exception) {
        }
    }


}
