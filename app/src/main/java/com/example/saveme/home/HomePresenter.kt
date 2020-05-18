package com.example.saveme.home

import android.content.Context
import android.util.Log
import com.example.saveme.community.CommunityAdapter
import com.example.saveme.community.CommunityModel
import com.example.saveme.model.GetCommunityList
import com.example.saveme.network.RetrofitClient
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomePresenter : HomeContract.Presenter {

    private var homeView: HomeContract.View? = null

    override fun takeView(view: HomeContract.View) {
        homeView = view
    }

    override fun dropView() {
        homeView = null
    }

    override fun loadItems(list: ArrayList<CommunityModel>, context: Context) {
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

                            if(communityList.community_category == "입양해주세요"){
                                Log.e("'입양해주세요' 카테고리 글 : ", communityList.community_title)
                                homeView!!.setAdoptItems(communityList.community_title, communityList.img1)

                            }else if(communityList.community_category == "임시보호요청"){
                                Log.e("'임시호보요청' 카테고리 글 : ", communityList.community_title)
                                homeView!!.setProtectionItems(communityList.community_title, communityList.img1)
                            }

                            /*var addData: CommunityModel = CommunityModel(
                                communityList.user_id,
                                communityList.user_name,
                                communityList.community_category,
                                communityList.community_date,
                                communityList.community_title,
                                communityList.community_content,
                                communityList.img1,
                                communityList.img2,
                                communityList.img3
                            )*/
                            Log.e("GET CommunityList : ", communityList.community_title)

                        }
                    }


                }


            }

            override fun onFailure(call: Call<List<GetCommunityList>>, t: Throwable) {
                Log.e("Community load fail", t.toString())
            }

        })

    }
}