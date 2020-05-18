package com.example.saveme.model

import com.google.gson.annotations.SerializedName

class GetCommunityList {
    @SerializedName("id")
    val id = 0
    @SerializedName("user_id")
    val user_id = 0
    @SerializedName("user_name")
    val user_name = ""
    @SerializedName("community_category")
    val community_category = ""
    @SerializedName("community_date")
    val community_date = ""
    @SerializedName("community_title")
    val community_title = ""
    @SerializedName("community_content")
    val community_content = ""
    @SerializedName("img1")
    val img1 = ""
    @SerializedName("img2")
    val img2: String? = ""
    @SerializedName("img3")
    val img3: String? = ""
}