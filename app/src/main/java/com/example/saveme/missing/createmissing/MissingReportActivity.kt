package com.example.saveme.missing.createmissing

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.saveme.R
import com.example.saveme.missing.MissingModel
import com.example.saveme.model.CreateMissing
import com.example.saveme.network.RetrofitClient
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_missing_report.*
import retrofit2.Call
import java.util.*
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat


class MissingReportActivity : AppCompatActivity() {

    var modifyId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_missing_report)

        // 툴바선언
        setSupportActionBar(toolbar_missing_report)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)  // 왼쪽버튼 사용 여부 true
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_pink_24dp)   // 왼쪽버튼 이미지 설정
        supportActionBar!!.setDisplayShowTitleEnabled(false)    // 기본 타이틀 사용 여부 false

        setData()
        pressedRegistrationBtn()

        // 상태(실종, 보호, 목격, 완료)
        missing_info_status.setOnClickListener {
            val items = arrayOf("실종", "보호", "목격", "완료")
            val builder = AlertDialog.Builder(this)
            with(builder) {
                setTitle("상태 선택")
                setItems(items) { dialogInterface, i ->
                    missing_info_status.text = items[i]
                }
                show()
            }
        }

        // 날짜
        missing_info_date.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)


            val dpd = DatePickerDialog(
                this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                    val month = monthOfYear + 1
                    val date = "$year - $month - $dayOfMonth"
                    missing_info_date.text = date

                },
                year,
                month,
                day
            )
            dpd.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dpd.show()
        }

        // 도시
        missing_info_city.setOnClickListener {
            val items = arrayOf(
                "경기도",
                "대구광역시",
                "대전광역시",
                "세종특별자치지",
                "인천광역시",
                "전라남도",
                "강원도",
                "울산광역시",
                "전라북도",
                "충청남도",
                "제주특별자치도",
                "경상남도",
                "경상북도",
                "충청북도",
                "모든 지역",
                "서울특별시",
                "광주광역시",
                "부산광역시"
            )
            val builder = AlertDialog.Builder(this)
            with(builder) {
                setTitle("도시 선택")
                setItems(items) { dialogInterface, i ->
                    missing_info_city.text = items[i]
                }
                show()
            }
        }
        // 시군구
        if (missing_info_city.text !== "모든 지역") {
            missing_info_district.visibility = View.VISIBLE
            missing_info_district.setOnClickListener {
                val items = arrayOf(
                    "성북구",
                    "마포구"
                )
                val builder = AlertDialog.Builder(this)
                with(builder) {
                    setTitle("시군구 선택")
                    setItems(items) { dialogInterface, i ->
                        missing_info_district.text = items[i]
                    }
                    show()
                }
            }
        }
        // 구체적인 장소
        missing_info_detail_location

        // 작성자 연락처
        missing_info_phone

        // 종(개,고양이,기타)
        missing_info_species.setOnClickListener {
            val items = arrayOf("개", "기타", "모든동물", "고양이")
            val builder = AlertDialog.Builder(this)
            with(builder) {
                setTitle("동물 선택")
                setItems(items) { dialogInterface, i ->
                    missing_info_species.text = items[i]
                }
                show()
            }
        }
        // 품종
        if (missing_info_species.text !== "모든 동물") {
            missing_info_breed.visibility = View.VISIBLE
            missing_info_breed.setOnClickListener {
                val items: Array<String>
                if (missing_info_species.text == "개") {
                    items = arrayOf("전체", "말티즈", "푸들")
                    val builder = AlertDialog.Builder(this)
                    with(builder) {
                        setTitle("품종 선택")
                        setItems(items) { dialogInterface, i ->
                            missing_info_breed.text = items[i]
                        }
                        show()
                    }
                } else if (missing_info_species.text == "고양이") {
                    items = arrayOf("전체", "코리안숏헤어")
                    val builder = AlertDialog.Builder(this)
                    with(builder) {
                        setTitle("품종 선택")
                        setItems(items) { dialogInterface, i ->
                            missing_info_breed.text = items[i]
                        }
                        show()
                    }
                } else if (missing_info_species.text == "기타") {
                    items = arrayOf("기타축종")
                    val builder = AlertDialog.Builder(this)
                    with(builder) {
                        setTitle("품종 선택")
                        setItems(items) { dialogInterface, i ->
                            missing_info_breed.text = items[i]
                        }
                        show()
                    }
                }
            }
        }

        // 성별
        missing_info_gender.setOnClickListener {
            val items = arrayOf("미확인", "수컷", "암컷")
            val builder = AlertDialog.Builder(this)
            with(builder) {
                setTitle("성별 선택")
                setItems(items) { dialogInterface, i ->
                    missing_info_gender.text = items[i]
                }
                show()
            }
        }

        // 중성화 여부
        missing_info_neuter.isChecked


        // 나이
        missing_info_age.setOnClickListener {
            val items = arrayOf("나이 모름", "1년 미만", "1살", "2살", "3살")
            val builder = AlertDialog.Builder(this)
            with(builder) {
                setTitle("나이 선택")
                setItems(items) { dialogInterface, i ->
                    missing_info_age.text = items[i]
                }
                show()
            }
        }

        // 몸무게
        missing_info_weight.setOnClickListener {
            val items = arrayOf("몸무게 모름", "1kg 미만", "1kg", "2kg", "3kg")
            val builder = AlertDialog.Builder(this)
            with(builder) {
                setTitle("몸무게 선택")
                setItems(items) { dialogInterface, i ->
                    missing_info_weight.text = items[i]
                }
                show()
            }
        }

        // 털 색
        missing_info_pattern

        // 특징
        missing_info_feature

        // 기타
        missing_info_etc

    }

    private fun pressedRegistrationBtn() {
        btn_missing_report_registration.setOnClickListener {

            val intent = Intent()
            if (missing_info_detail_location.text.isEmpty() && missing_info_phone.text.isEmpty() && missing_info_pattern.text.isEmpty() && missing_info_feature.text.isEmpty() && missing_info_etc.text.isEmpty()) {
                Toast.makeText(this, "비어있는 칸을 채워주세요", Toast.LENGTH_SHORT).show()
            } else {
                if (modifyId != -1) {
                    intent.putExtra("id", modifyId)
                }
                intent.putExtra("status", missing_info_status.text.toString())
                intent.putExtra("date", missing_info_date.text.toString())
                intent.putExtra("city", missing_info_city.text.toString())
                intent.putExtra("district", missing_info_district.text.toString())
                intent.putExtra("detail_location", missing_info_detail_location.text.toString())
                intent.putExtra("phone", missing_info_phone.text.toString())
                intent.putExtra("species", missing_info_species.text.toString())
                intent.putExtra("breed", missing_info_breed.text.toString())
                intent.putExtra("gender", missing_info_gender.text.toString())
                intent.putExtra("neuter", false)
                intent.putExtra("age", missing_info_age.text.toString())
                intent.putExtra("weight", missing_info_weight.text.toString())
                intent.putExtra("pattern", missing_info_pattern.text.toString())
                intent.putExtra("feature", missing_info_feature.text.toString())
                intent.putExtra("etc", missing_info_etc.text.toString())
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
    }

    private fun setData(){  // 수정시 저장되어있던 데이터들을 빈칸에 입력해주는 코드
        if (intent.hasExtra("status")) {
            actionBar?.title = "실종/보호 신고 수정"
            modifyId = intent.getIntExtra("id", -1)
            Log.e("주제수정 id", modifyId.toString())
            missing_info_status.text = intent.getStringExtra("status")

            // datetime 에서 date 만 뽑아와서 출력
            val outputFormat = SimpleDateFormat("yyyy-MM-dd")
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S")

            val inputText = intent.getStringExtra("date")
            val date = inputFormat.parse(inputText)
            val outputText = outputFormat.format(date)
            missing_info_date.text = outputText

            missing_info_city.text = intent.getStringExtra("city")
            missing_info_district.text = intent.getStringExtra("district")
            missing_info_detail_location.setText(intent.getStringExtra("detail_location"))
            missing_info_phone.setText(intent.getStringExtra("phone"))
            missing_info_species.text = intent.getStringExtra("species")
            missing_info_breed.text = intent.getStringExtra("breed")
            missing_info_gender.text = intent.getStringExtra("gender")
            if (intent.getBooleanExtra("neuter", false)){
                missing_info_neuter.isChecked = true
                Log.e("tr","")
            } else {
                Log.e("fa", "")
            }
            missing_info_age.text = intent.getStringExtra("age")
            missing_info_weight.text = intent.getStringExtra("weight")
            missing_info_pattern.setText(intent.getStringExtra("pattern"))
            missing_info_feature.setText(intent.getStringExtra("feature"))
            missing_info_etc.setText(intent.getStringExtra("etc"))
        }
    }

}
