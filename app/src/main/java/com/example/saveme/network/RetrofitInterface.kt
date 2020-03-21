package com.example.saveme.network

import com.example.saveme.missing.MissingModel
import com.example.saveme.model.GetMissingList
import com.example.saveme.model.ShelterModel
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.DELETE




interface RetrofitInterface {

    /*
    @Path = (@Path("pk") int pk)에서 pk는 윗줄에 rest api 어노테이션 뒤에 사용하는 변수
    @Query = (@Query("example") String test)에서 test는 변수, 입력을 aaaa로 받았을때 url은 xxxxx?esample=aaaa
    @Field = (@Field("email") String test)에서 Test는 데이터베이스의 필드 하나에 해당함. 즉 email 레코드의 필드 하나 값
    @FormUrlEncoded = 입력된 스트링이나 해시맵을 데이터베이스에 반영될 수 있도록 인코딩해줌
    */

    // 로그인
    @GET("/user")
    fun logIn(@Query("userEmail") userEmail: String): Call<List<Json_User>>

/*    @FormUrlEncoded
    @POST("user/")
    fun logIn(@Field("userEmail") userEmail : String,
              @Field("userPw") userPw : String): Call<List<Json_User>>*/

    // 회원가입
    @FormUrlEncoded
    @POST("/user/")
    fun signUp(
        @Field("userEmail") userEmail: String,
        @Field("userName") userName: String,
        @Field("userPw") userPw: String
    ): Call<ResponseBody>

    // 보호소 동물 리스트 받아오기
    @GET("/shelters")
    fun requestShelterData(): Call<List<ShelterModel>>

    // 실종동물 글 리스트 조회
    @GET("/missings")
    fun getMissingData(): Call<List<GetMissingList>>

    // 실종동물 글 작성
//    @POST("/missings")
//    fun createMissingData(@Body createMissingData: MissingModel): Call<MissingModel>
    @FormUrlEncoded
    @POST("/missings")
    fun createMissingData(
        @Field("status") status: String,
        @Field("date") date: String,
        @Field("city") city: String,
        @Field("district") district: String,
        @Field("detailLocation") detailLocation: String,
        @Field("phone") phone: String,
        @Field("species") species: String,
        @Field("breed") breed: String,
        @Field("gender") gender: String,
        @Field("neuter") neuter: String,
        @Field("age") age: String,
        @Field("weight") weight: String,
        @Field("pattern") pattern: String,
        @Field("feature") feature: String,
        @Field("etc") etc: String
    ): Call<MissingModel>

    // 실동종물 글 수정
    @PUT("/missings/{pk}")
    fun updateMissingData(@Path("pk") pk: Int): Call<MissingModel>

    // 실종동물 글 삭제하기
    @DELETE("/missings/{pk}")
    fun deleteMissingData(@Path("pk") pk: Int): Call<MissingModel>


















    @FormUrlEncoded
    @POST("User")
//    fun post_User(@FieldMap param: HashMap<String, Any>): Call<Json_User>
    fun post_User(@Query("format") json: String, @Body json_user: Json_User): Call<Json_User>

    @FormUrlEncoded
    @POST("User")
    fun post_User2(@Field("email") user: String): Call<ResponseBody>

    @FormUrlEncoded
    @PATCH("User/{pk}/")
    fun patch_Test(@Path("pk") pk: Int, @Query("format") json: String, @Field("email") User: String): Call<ResponseBody>

    @DELETE("User/{pk}/")
    fun delete_Patch_User(@Path("pk") pk: Int, @Query("format") json: String): Call<ResponseBody>


}