package com.example.saveme.model

import com.google.gson.annotations.SerializedName

data class User(
    var email: String,
    var name: String,
    var pw: String
)

class GetUser {
    @SerializedName("user")
    var user: userData? = null
    @SerializedName("token")
    var token: String? = null
}

class userData {
    @SerializedName("id")
    var id: String = ""
    @SerializedName("username")
    var username: String = ""
    @SerializedName("email")
    var email: String = ""
}
