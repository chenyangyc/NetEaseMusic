package com.example.neteasecloudmusic.model

import android.content.SharedPreferences

val LOGIN = 1
object ShareCenter {
    fun updateShareInfo(share: SharedPreferences, shareData:LoginData , password:String , phoneNum:String){
        share.edit()
            .putString("avatarUrl",shareData.profile?.avatarUrl)
            .putString("nickname", shareData.profile?.nickname)
            .putInt("gender", shareData.profile?.gender ?: 0)
            .putString("phoneNum", phoneNum)
            .putString("password", password)
            .putInt("id", shareData.account!!.id)
            .putInt("status", LOGIN)
            .putString("background_url", shareData.profile?.backgroundUrl).apply()
    }
}