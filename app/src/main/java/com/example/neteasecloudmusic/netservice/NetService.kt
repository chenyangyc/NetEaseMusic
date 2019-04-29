package com.example.neteasecloudmusic.netservice

import com.example.neteasecloudmusic.model.*
import retrofit2.http.GET
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Query


interface NetService {

    @GET("/login/cellphone")
    fun getStatus(@Query("phone") phoneNum: String, @Query("password") pwd: String): Call<LoginData>

    @GET("/logout")
    fun logout(): Call<ResponseBody>

    @GET("/user/playlist")
    fun getPlayList(@Query("uid") userID: Int): Call<MyPlayListBean>

    @GET("/playlist/detail")
    fun getPlayListDetail(@Query("id") listId: String): Call<PlayListDetailBean>

    @GET("/song/detail")
    fun getSongDetail(@Query("ids") songId: String): Call<SongDetailBean>

    companion object : NetService by MainModel()
}