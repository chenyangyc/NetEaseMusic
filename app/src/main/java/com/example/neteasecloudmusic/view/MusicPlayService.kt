package com.example.neteasecloudmusic.view

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder

class MusicPlayService :Service(){
    private var playList: ArrayList<String?> = ArrayList()
    private var id: String? = ""
    private var url: String? = ""
    private var position = -1
    var mediaPlayer: MediaPlayer? = null
    var tag : Boolean ?= true

    //  通过 Binder 来保持 Activity 和 Service 的通信
    var binder = MyBinder()

    inner class MyBinder : Binder() {
        internal val service: MusicPlayService
            get() = this@MusicPlayService
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        id = intent?.getStringExtra("url")
        if (id != null) {
            play(id)
            mediaPlayer?.setOnErrorListener { _, _, _ ->
                play(id)
                false
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    fun play(url: String?): Boolean {
        when (mediaPlayer) {
            null -> mediaPlayer = MediaPlayer()
        }
        mediaPlayer?.let {
            it.reset()
            try {
                it.setDataSource(url)
                it.prepare()
                it.start()
                if (!playList.contains(url)) {
                    playList.add(url)
                    position++
                }
                return true
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return false
    }

    fun playOrPause() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.pause()
                tag = true
            } else {
                it.start()
                tag = false
            }
        }
    }
}