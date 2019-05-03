package com.example.neteasecloudmusic.view

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder

class MusicPlayService :Service(){

    //  通过 Binder 来保持 Activity 和 Service 的通信
    var binder = MyBinder()

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    var mediaPlayer: MediaPlayer? = null
    var tag = false

    init {
        mediaPlayer = MediaPlayer()
        try {
            mediaPlayer!!.setDataSource("/data/music.mp3")
            mediaPlayer!!.prepare()
            mediaPlayer!!.setLooping(true)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    inner class MyBinder : Binder() {
        internal val service: MusicPlayService
            get() = this@MusicPlayService
    }

    fun playOrPause() {
        if (mediaPlayer!!.isPlaying()) {
            mediaPlayer!!.pause()
        } else {
            mediaPlayer!!.start()
        }
    }

    fun stop() {
        if (mediaPlayer != null) {
            mediaPlayer!!.stop()
            try {
                mediaPlayer!!.reset()
                mediaPlayer!!.setDataSource("/data/music.mp3")
                mediaPlayer!!.prepare()
                mediaPlayer!!.seekTo(0)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}