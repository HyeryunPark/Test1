package com.example.saveme.popup

import android.content.Context
import android.util.Log
import com.example.saveme.model.GetPopUp
import com.example.saveme.network.RetrofitClient
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopUpPresenter : PopUpContract.Presenter {

    private var popupView: PopUpContract.View? = null

    override fun takeView(view: PopUpContract.View) {
        popupView = view
    }

    override fun dropView() {
        popupView = null
    }

    override fun loadNews(context: Context) {
        val client: OkHttpClient = RetrofitClient.getClient(context, "")
        val retrofitInterface = RetrofitClient.retrofitInterface(client)

        val request: Call<List<GetPopUp>> = retrofitInterface.loadNewsData()
        request.enqueue(object : Callback<List<GetPopUp>> {
            override fun onResponse(
                call: Call<List<GetPopUp>>,
                response: Response<List<GetPopUp>>
            ) {
                if (response.isSuccessful) {
                    Log.e("Success", Gson().toJson(response.body()))

                    val body = response.body()
                    body?.let {
                        var count = 0
                        Log.e("body.size", body.size.toString())

                        for (i in 0 until body.size) {
                            var popupList: GetPopUp = GetPopUp()
                            popupList = body[i]
                            Log.e("NEWS", popupList.title)

                            count++
                            if (count == 1) {
                                popupView?.setNewsData1(popupList.title)
                                Log.e("count = 1", popupList.title)
                            } else if (count == 2) {
                                popupView?.setNewsData2(popupList.title)
                                Log.e("count = 2", popupList.title)

                            } else if (count == 3) {
                                popupView?.setNewsData3(popupList.title)
                                Log.e("count = 3", popupList.title)

                            } else if (count == 4) {
                                popupView?.setNewsData4(popupList.title)
                                Log.e("count = 4", popupList.title)

                            } else if (count == 5) {
                                popupView?.setNewsData5(popupList.title)
                                Log.e("count = 5", popupList.title)

                            } else {
                                return
                            }

                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<GetPopUp>>, t: Throwable) {
                Log.e("Fail", t.toString())
            }
        })
    }

}