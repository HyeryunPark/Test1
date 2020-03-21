package com.example.saveme.missing

import android.util.Log
import com.example.saveme.model.GetMissingList
import com.example.saveme.network.RetrofitClient
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MissingPresenter : MissingContract.Presenter {

    private var missingView: MissingContract.View? = null
//    private val retrofitInterface = RetrofitClient.retrofitInterface

    override fun takeView(view: MissingContract.View) { // View와 Presenter를 연결해준다. (missingView = view)
        missingView = view
    }

    override fun dropView() {   // View가 제거된 것을 Presenter에 알려준다.
        missingView = null
    }

/*    override fun loadItems(adapter: MissingAdapter, list: ArrayList<MissingModel>) {
        val client: OkHttpClient = OkHttpClient()
        val retrofitInterface = RetrofitClient.retrofitInterface(client)
        val rr: Call<JsonObject> = retrofitInterface.getMissingData()
        rr.enqueue(object : Callback<JsonObject>{
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if(response.isSuccessful){
                    Log.e("Success", Gson().toJson(response.body()))

                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.e("실종동물 정보 받아오기 실패", t.toString())
            }

        })


    }*/

    override fun loadItems(adapter: MissingAdapter, list: ArrayList<MissingModel>) {  // 글 전체 불러오기
        val client: OkHttpClient = OkHttpClient()
        val retrofitInterface = RetrofitClient.retrofitInterface(client)

        val request: Call<List<GetMissingList>> = retrofitInterface.getMissingData()
        request.enqueue(object : Callback<List<GetMissingList>> {
            override fun onResponse(call: Call<List<GetMissingList>>,response: Response<List<GetMissingList>>) {
                if (response.isSuccessful) {
                    Log.e("Success", Gson().toJson(response.body()))

                    val body = response.body()
                    body?.let {
                        Log.e("잘들어옴", ".")
                        for (i in 0 until body.size) {
                            var missingList: GetMissingList = GetMissingList()
                            missingList = body[i]
                            var addData: MissingModel = MissingModel(
                                missingList.id,
                                missingList.status,
                                missingList.date,
                                missingList.city,
                                missingList.district,
                                missingList.detailLocation,
                                missingList.phone,
                                missingList.species,
                                missingList.breed,
                                missingList.gender,
                                missingList.neuter,
                                missingList.age,
                                missingList.weight,
                                missingList.pattern,
                                missingList.feature,
                                missingList.etc
                            )
                            Log.e("들어오니?", "missingList.id"+ missingList.id)
                            adapter.addItem(addData)
                            missingView!!.refresh()
                        }

                    }
                }
            }

            override fun onFailure(call: Call<List<GetMissingList>>, t: Throwable) {
                Log.e("실종동물 정보 받아오기 실패", t.toString())
            }

        })
    }


    override fun addItems() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateItems() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteItems() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}