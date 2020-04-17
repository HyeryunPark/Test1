package com.example.saveme.popup

import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import com.example.saveme.R
import com.example.saveme.home.HomeActivity
import kotlinx.android.synthetic.main.activity_pop_up.*
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.xml.sax.InputSource
import java.net.URL
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.xml.parsers.DocumentBuilderFactory

class PopUpActivity : AppCompatActivity() {

    private lateinit var openApiTask: OpenApiTask

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pop_up)

        btn_start.setOnClickListener {
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        openApiTask = OpenApiTask()
        openApiTask.execute()

    }

    private inner class OpenApiTask : AsyncTask<String, Void, Document>() {

        internal lateinit var doc: Document

        internal var todayrescue: Int = 0
        internal var euthanasia: Float = 0.00F
        internal var adopt: Float = 0.00F
        internal var totalCount: Int = 0

        val currentDate = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        val formatted = currentDate.format(formatter)


        override fun doInBackground(vararg urls: String): Document {
            val url: URL
            val serviceKey: String =
                "0%2FM8izCFBhlkSchEwF46N%2FuD%2BUjjLYCwSLTtqUWTQ0S3w6ws4sm3RuyzFW3nNPIfHV6ZbiWqx5v5DtzCemQyng%3D%3D"

            try {
                url =
                    URL("http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/abandonmentPublic?serviceKey=$serviceKey&bgnde=20200101&endde=$formatted&pageNo=1&numOfRows=100000000")

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

            val nodeList = doc.getElementsByTagName("item")

            totalCount = nodeList.length

            for (i in 0 until nodeList.length) {

                var n: Node = nodeList.item(i)

                if (n.nodeType == Node.ELEMENT_NODE) {

                    val elem = n as Element
                    val map = mutableMapOf<String, String>()

                    for (j in 0 until elem.attributes.length) {

                        map.putIfAbsent(
                            elem.attributes.item(j).nodeName,
                            elem.attributes.item(j).nodeValue
                        )
                    }

                    val processState: String =
                        elem.getElementsByTagName("processState").item(0).textContent
                    val happenDt: String = elem.getElementsByTagName("happenDt").item(0).textContent

                    if (happenDt == "$formatted") {
                        todayrescue++
                    }
                    if (processState == "종료(안락사)") {
                        euthanasia++
                    } else if (processState == "종료(입양)") {
                        adopt++
                    }

                }


                super.onPostExecute(doc)
            }
            val percenteuthanasia = String.format("%.1f", euthanasia * 100 / totalCount)
            val percentadopt = String.format("%.1f", adopt * 100 / totalCount)

            Log.e(
                "PopUp",
                "오늘 구조된 동물 수 : $todayrescue, 올해 입양률 : $percentadopt%, 올해 안락사율 : $percenteuthanasia% "
            )

//            tv_rescued_number.text = "오늘 구조된 동물\n $todayrescue 마리"
            tv_rescued_number.text = Html.fromHtml("오늘 구조된 동물<br /> <font color=\"#000000\">$todayrescue</font> 마리", Html.FROM_HTML_MODE_LEGACY)
//            tv_adoption_rate.text = "입양률 <font color=\"#04B404\">$percenteuthanasia</font>%"
            tv_adoption_rate.text = Html.fromHtml("입양률 <font color=\"#009900\">$percenteuthanasia</font> %", Html.FROM_HTML_MODE_LEGACY)
//            tv_euthanasia_rate.text = "안락사율 <font color=\"#DF0101\">$percentadopt</font>%"
            tv_euthanasia_rate.text = Html.fromHtml("안락사율 <font color=\"#FF0000\">$percentadopt</font> %", Html.FROM_HTML_MODE_LEGACY)


        }

    }

    override fun onDestroy() {
        super.onDestroy()
        // 실행중인 AsyncTask의 상태를 얻어와서 RUNNING중이면 cancel 시켜버리겠다
        try {
            if (openApiTask.status == AsyncTask.Status.RUNNING)
                openApiTask.cancel(true)
        } catch (e: Exception) {
        }
    }
}
