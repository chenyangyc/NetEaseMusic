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

    fun getPlayList(userId: Int, back: (Status, MyPlayListBean?) -> (Unit)) {
        val call = NetService.getPlayList(userId)
        launch {
            try {
                val getPlayListBean = call.execute().body()
                if (getPlayListBean == null)
                    back(Status.UNMATCHED, null)
                else
                    back(Status.SUCCESS, getPlayListBean)
            } catch (e: Exception) {
                e.printStackTrace()
                back(Status.ERROR, null)
            }
        }
    }

    fun getPlayListDetail(listId: String, back: (Status, PlayListDetailBean?) -> (Unit)) {
        val call = NetService.getPlayListDetail(listId)
        launch {
            try {
                val playListDetailBean = call.execute().body()
                if (playListDetailBean == null)
                    back(Status.UNMATCHED, null)
                else
                    back(Status.SUCCESS, playListDetailBean)
            } catch (e: Exception) {
                e.printStackTrace()
                back(Status.ERROR, null)
            }
        }
    }

    fun getSongDetail(songId: String, back: (Status, SongDetailBean?) -> (Unit)) {
        val call = NetService.getSongDetail(songId)
        launch {
            try {
                val songDetailBean = call.execute().body()
                if(songDetailBean == null)
                    back(Status.UNMATCHED, null)
                else
                    back(Status.SUCCESS,songDetailBean)
            } catch(e:Exception) {
                back(Status.ERROR,null)
            }
        }
    }

    fun getMusicURL(id: String?, MusicData: (Status, MusicBean?) -> (Unit)) {
        val call = NetService.getMusicURL(id)
        launch {
            try {
                val musicBean = call.execute().body()
                if (musicBean == null)
                    MusicData(Status.UNMATCHED, null)
                else
                    MusicData(Status.SUCCESS, musicBean)
            } catch (e: Exception) {
                MusicData(Status.ERROR, null)
                e.printStackTrace()
            }
        }
    }

    fun checkMusic(id: String, checkData: (Status, CheckMusicBean?) -> (Unit)) {
        val call = NetService.checkMusic(id)
        launch {
            try {
                val checkBean = call.execute().body()
                if (checkBean == null)
                    checkData(Status.UNMATCHED, null)
                else
                    checkData(Status.SUCCESS, checkBean)
            } catch (e: Exception) {
                checkData(Status.ERROR, null)
                e.printStackTrace()
            }
        }
    }

    fun getMusicDetail(ids: String, checkData: (Status, SongDetailBean?) -> (Unit)) {
        val call = NetService.getMusicDetail(ids)
        launch {
            try {
                val songDetailBean = call.execute().body()
                if (songDetailBean == null)
                    checkData(Status.UNMATCHED, null)
                else
                    checkData(Status.SUCCESS, songDetailBean)
            } catch (e: Exception) {
                checkData(Status.ERROR, null)
                e.printStackTrace()
            }
        }
    }

}