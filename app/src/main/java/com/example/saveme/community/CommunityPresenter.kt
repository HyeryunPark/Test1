package com.example.saveme.community

import android.content.Context
import android.util.Log
import com.example.saveme.model.CreateCommunity
import com.example.saveme.model.GetCommunityList
import com.example.saveme.network.RetrofitClient
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class CommunityPresenter : CommunityContract.Presenter {

    private var communityView: CommunityContract.View? = null

    override fun takeView(view: CommunityContract.View) {
        communityView = view
    }

    override fun dropView() {
        communityView = null
    }

    override fun loadItems(adapter: CommunityAdapter, list: ArrayList<CommunityModel>, context: Context) {
        val client: OkHttpClient = RetrofitClient.getClient(context, "")
        val retrofitInterface = RetrofitClient.retrofitInterface(client)

        val request: Call<List<GetCommunityList>> = retrofitInterface.getCommunityData()
        request.enqueue(object : Callback<List<GetCommunityList>> {
            override fun onResponse(call: Call<List<GetCommunityList>>, response: Response<List<GetCommunityList>>) {
                if (response.isSuccessful) {
                    Log.e("Community load Success", Gson().toJson(response.body()))

                    val body = response.body()
                    body?.let {
                        for (i in 0 until body.size) {
                            var communityList: GetCommunityList = GetCommunityList()
                            communityList = body[i]

                            var addData: CommunityModel = CommunityModel(
                                communityList.user_id,
                                communityList.user_name,
                                communityList.community_category,
                                communityList.community_date,
                                communityList.community_title,
                                communityList.community_content,
                                communityList.img1,
                                communityList.img2,
                                communityList.img3
                            )
                            Log.e("GET CommunityList : ", communityList.community_title)
                            adapter.addItem(addData)
                            communityView!!.refresh()
                        }
                    }

                }

            }

            override fun onFailure(call: Call<List<GetCommunityList>>, t: Throwable) {
                Log.e("Community load fail", t.toString())
            }

        })
    }

    override fun addItems(createCommunity: CreateCommunity, context: Context, adapter: CommunityAdapter, list: ArrayList<CommunityModel>) {
        val client: OkHttpClient = RetrofitClient.getClient(context, "")
        val retrofitInterface = RetrofitClient.retrofitInterface(client)

        lateinit var register_request: Call<CreateCommunity>

        var multiPartBody2: MultipartBody.Part? = null
        var multiPartBody3: MultipartBody.Part? = null

        val img1 = File(createCommunity.img1)
        val requestBody1 = RequestBody.create(MediaType.parse("multipart/data"), img1)
        val multipartBody1 = MultipartBody.Part.createFormData("img1", img1.name, requestBody1)

        if (createCommunity.img2 != "null" && createCommunity.img3 == "null") {
            val img2 = File(createCommunity.img2)
            val requestBody2 = RequestBody.create(MediaType.parse("multipart/data"), img2)
            multiPartBody2 = MultipartBody.Part.createFormData("img2", img2.name, requestBody2)

            register_request = retrofitInterface.createCommunityData(
                0,
                RequestBody.create(MediaType.parse("multipart/data"), createCommunity.category),
                RequestBody.create(MediaType.parse("multipart/data"), createCommunity.title),
                RequestBody.create(MediaType.parse("multipart/data"), createCommunity.content),
                multipartBody1, multiPartBody2, null
            )
        }
        if (createCommunity.img2 != "null" && createCommunity.img3 != "null") {
            val img2 = File(createCommunity.img2)
            val requestBody2 = RequestBody.create(MediaType.parse("multipart/data"), img2)
            multiPartBody2 = MultipartBody.Part.createFormData("img2", img2.name, requestBody2)

            val img3 = File(createCommunity.img3)
            val requestBody3 = RequestBody.create(MediaType.parse("multipart/data"), img3)
            multiPartBody3 = MultipartBody.Part.createFormData("img3", img2.name, requestBody3)

            register_request = retrofitInterface.createCommunityData(
                0,
                RequestBody.create(MediaType.parse("multipart/data"), createCommunity.category),
                RequestBody.create(MediaType.parse("multipart/data"), createCommunity.title),
                RequestBody.create(MediaType.parse("multipart/data"), createCommunity.content),
                multipartBody1, multiPartBody2, multiPartBody3
            )
        }
        if (createCommunity.img2 == "null" && createCommunity.img3 == "null") {

            register_request = retrofitInterface.createCommunityData(
                0,
                RequestBody.create(MediaType.parse("multipart/data"), createCommunity.category),
                RequestBody.create(MediaType.parse("multipart/data"), createCommunity.title),
                RequestBody.create(MediaType.parse("multipart/data"), createCommunity.content),
                multipartBody1, null, null )
        }

        register_request.enqueue(object : Callback<CreateCommunity> {

            override fun onResponse(call: Call<CreateCommunity>, response: Response<CreateCommunity>) {
                if (response.isSuccessful) {
                    Log.e("Success(글 추가)", "")
                    list.clear()
                    loadItems(adapter, list, context)
                    communityView!!.refresh()
                } else {

                }
            }
            override fun onFailure(call: Call<CreateCommunity>, t: Throwable) {
                Log.e("Fail(글 추가)", t.toString())
            }

        })


    }

    override fun updateItems(
        pk: Int,
        communityModel: CommunityModel,
        context: Context,
        adapter: CommunityAdapter,
        list: ArrayList<CommunityModel>
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteItems(pk: Int, context: Context) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}