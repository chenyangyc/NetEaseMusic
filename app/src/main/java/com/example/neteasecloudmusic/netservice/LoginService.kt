package com.example.neteasecloudmusic.netservice

import com.example.neteasecloudmusic.model.*
import kotlinx.coroutines.launch
import java.lang.Exception



object LoginService {
    fun login(phone: String, pwd: String, back: (LoginStatus, LoginData?) -> (Unit)) {
        val call = NetService.getStatus(phone, pwd)
        launch {
            try {
                val loginDataBean = call.execute().body()
                if (loginDataBean == null)
                    back(LoginStatus.WRONGPASSWORD, null)
                else
                    back(LoginStatus.SUCCESS, loginDataBean)
            } catch (e: Exception) {
                e.printStackTrace()
                back(LoginStatus.ERROR, null)
            }
        }
    }

    fun logout(){
        val call = NetService.logout()
        launch {
            call.execute().body()
        }
    }

    fun getPlayList(userId: Int, back: (GetPlayListStatus, MyPlayListBean?) -> (Unit)) {
        val call = NetService.getPlayList(userId)
        launch {
            try {
                val getPlayListBean = call.execute().body()
                if (getPlayListBean == null)
                    back(GetPlayListStatus.UNMATCHED, null)
                else
                    back(GetPlayListStatus.SUCCESS, getPlayListBean)
            } catch (e: Exception) {
                e.printStackTrace()
                back(GetPlayListStatus.ERROR, null)
            }
        }
    }

    fun getPlayListDetail(listId: String, back: (GetPlayListStatus, PlayListDetailBean?) -> (Unit)) {
        val call = NetService.getPlayListDetail(listId)
        launch {
            try {
                val playListDetailBean = call.execute().body()
                if (playListDetailBean == null)
                    back(GetPlayListStatus.UNMATCHED, null)
                else
                    back(GetPlayListStatus.SUCCESS, playListDetailBean)
            } catch (e: Exception) {
                e.printStackTrace()
                back(GetPlayListStatus.ERROR, null)
            }
        }
    }

    fun getSongDetail(songId: String, back: (GetPlayListStatus, SongDetailBean?) -> (Unit)) {
        val call = NetService.getSongDetail(songId)
        launch {
            try {
                val songDetailBean = call.execute().body()
                if(songDetailBean == null)
                    back(GetPlayListStatus.UNMATCHED, null)
                else
                    back(GetPlayListStatus.SUCCESS,songDetailBean)
            } catch(e:Exception) {
                back(GetPlayListStatus.ERROR,null)
            }
        }
    }

}