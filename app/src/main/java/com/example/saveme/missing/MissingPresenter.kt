package com.example.saveme.missing

import android.content.Context
import android.util.Log
import com.example.saveme.model.CreateMissing
import com.example.saveme.model.GetMissingList
import com.example.saveme.network.RetrofitClient
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
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

    override fun loadItems(adapter: MissingAdapter, list: ArrayList<MissingModel>, context: Context) {  // 글 전체 불러오기
//        val client: OkHttpClient = OkHttpClient()
        val client: OkHttpClient = RetrofitClient.getClient(context, "")
        val retrofitInterface = RetrofitClient.retrofitInterface(client)

        val request: Call<List<GetMissingList>> = retrofitInterface.getMissingData()
        request.enqueue(object : Callback<List<GetMissingList>> {
            override fun onResponse(call: Call<List<GetMissingList>>, response: Response<List<GetMissingList>>) {
                if (response.isSuccessful) {
                    Log.e("Success", Gson().toJson(response.body()))

                    val body = response.body()
                    body?.let {
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
                            Log.e("들어오니?", "missingList.id : " + missingList.id)
                            adapter.addItem(addData)
                            missingView!!.refresh()
                        }

                    }
                }
            }

            override fun onFailure(call: Call<List<GetMissingList>>, t: Throwable) {
                Log.e("Fail", t.toString())
            }

        })
    }


    override fun addItems(status: String, date: String, city: String, district: String, detailLocation: String, phone: String, species: String, breed: String, gender: String, neuter: Boolean, age: String, weight: String, pattern: String, feature: String, etc: String,
                          context: Context, adapter: MissingAdapter, list: ArrayList<MissingModel>) {   // 글 작성하기
        val client: OkHttpClient = RetrofitClient.getClient(context, "")
        val retrofitInterface = RetrofitClient.retrofitInterface(client)

        val createMissing: CreateMissing = CreateMissing(status, date, city, district, detailLocation, phone, species, breed, gender, neuter, age, weight, pattern, feature, etc)
        val request: Call<CreateMissing> = retrofitInterface.createMissingData(createMissing)
        request.enqueue(object : Callback<CreateMissing> {
            override fun onResponse(call: Call<CreateMissing>, response: Response<CreateMissing>) {
                if (response.isSuccessful) {
                    Log.e("Success(글 추가)", "")
                    list.clear()
                    loadItems(adapter, list, context)
                    missingView!!.refresh()
                } else {

                }
            }

            override fun onFailure(call: Call<CreateMissing>, t: Throwable) {
                Log.e("Fail(글 추가)", t.toString())
            }

        })
    }

    override fun updateItems(
        pk: Int,
        missingModel: MissingModel,
        context: Context,
        adapter: MissingAdapter,
        list: ArrayList<MissingModel>
    ) {    // 글 수정하기
        Log.e("missing UPDATE", "$pk")
        val client: OkHttpClient = RetrofitClient.getClient(context, "")
        val retrofitInterface = RetrofitClient.retrofitInterface(client)

        val request: Call<MissingModel> = retrofitInterface.updateMissingData(pk, missingModel)
        request.enqueue(object : Callback<MissingModel> {
            override fun onResponse(call: Call<MissingModel>, response: Response<MissingModel>) {
                if (response.isSuccessful) {
                    Log.e("Success(글 수정)", "")
                    list.clear()
                    loadItems(adapter, list, context)
                    missingView!!.refresh()
                }
            }

            override fun onFailure(call: Call<MissingModel>, t: Throwable) {
                Log.e("Fail(수정)", t.toString())
            }
        })


    }

    override fun deleteItems(pk: Int, context: Context) {     // 글 삭제하기
        Log.e("missing DELETE", "$pk")
        val client: OkHttpClient = RetrofitClient.getClient(context, "")
        val retrofitInterface = RetrofitClient.retrofitInterface(client)

        val request: Call<ResponseBody> = retrofitInterface.deleteMissingData(pk)
        request.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Log.e("Success(글 삭제)", "")
                    missingView!!.refresh()
                } else {
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("Fail(삭제)", t.toString())
            }
        })
    }

    override fun modifyActivity(id: Int, missingModel: MissingModel) {  // 수정할 화면을 띄우는
        missingView?.modifyActivity(id, missingModel)
    }


}