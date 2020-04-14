package com.example.saveme.shelter

import android.content.Context
import android.util.Log
import com.example.saveme.model.GetShelterList
import com.example.saveme.network.RetrofitClient
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShelterPresenter : ShelterContract.Presenter {

    private var shelterView: ShelterContract.View? = null

    override fun takeView(view: ShelterContract.View) { // view 와 presenter 를 연결해줌
        shelterView = view
    }

    override fun dropView() {   // view 가 제거된 것을 presenter 에 알려준다.
        shelterView = null
    }

    override fun openApiTask() {    // 공공데이터포털 오픈API를 사용하는 함수

    }

    override fun getAbandonedPetsParsing(adapter: ShelterAdapter, list: ArrayList<ShelterModel>, context: Context) {
        val client: OkHttpClient = RetrofitClient.getClient(context, "")
        val retrofitInterface = RetrofitClient.retrofitInterface(client)

        val request: Call<List<GetShelterList>> = retrofitInterface.getShelterData()
        request.enqueue(object : Callback<List<GetShelterList>>{
            override fun onResponse(call: Call<List<GetShelterList>>, response: Response<List<GetShelterList>>) {
                if (response.isSuccessful) {
                    Log.e("Success", Gson().toJson(response.body()))


                }
            }

            override fun onFailure(call: Call<List<GetShelterList>>, t: Throwable) {
                Log.e("Fail", t.toString())
            }

        })
    }


}