package com.example.neteasecloudmusic.view

import android.animation.ObjectAnimator
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.Toast
import com.example.neteasecloudmusic.R
import com.example.neteasecloudmusic.model.GetPlayListStatus
import com.example.neteasecloudmusic.model.SongDetailBean
import com.example.neteasecloudmusic.netservice.LoginService
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.music_play_layout.*
import kotlinx.coroutines.android.UI
import kotlinx.coroutines.launch

class MusicPlayActivity : AppCompatActivity() {
    internal lateinit var id: String
    private var songDetail = SongDetailBean()

    private var musicPlayService: MusicPlayService ? = null

    private var tag1 = false
    private var tag2 = false
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.music_play_layout)
        supportActionBar?.hide()

        val i = intent
        val b = i.extras
        if (b != null) {
            id = b.getString("id")
        }

        Hawk.init(this)
            .build()

        initLayout()

        bindServiceConnection()
        myListener()
    }

    //  回调onServiceConnected 函数，通过IBinder 获取 Service对象，实现Activity与 Service的绑定
    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            musicPlayService = (service as MusicPlayService.MyBinder).service
        }

        override fun onServiceDisconnected(name: ComponentName) {
            musicPlayService = null
        }
    }

    //  在Activity中调用 bindService 保持与 Service 的通信
    private fun bindServiceConnection() {
        val intent = Intent(this@MusicPlayActivity, MusicPlayService::class.java)
        val bundle = Bundle()
        bundle.putString("id", id)
        intent.putExtras(bundle)
        startService(intent)
        bindService(intent, serviceConnection, BIND_AUTO_CREATE)
    }

    private fun myListener(){
        play_button.setOnClickListener {

//            val animator = ObjectAnimator.ofFloat(imageView, "rotation", 0f, 360.0f)
//            animator.duration = 10000
//            animator.interpolator = LinearInterpolator()
//            animator.repeatCount = -1
            //  由tag的变换来控制事件的调用
            if (musicPlayService!!.tag !== true) {
                play_button.text = "PAUSE"
                play_button.text = "Playing"
                musicPlayService!!.playOrPause()
                musicPlayService!!.tag = true

                if (tag1 == false) {
//                    animator.start()
                    tag1 = true
                } else {
//                    animator.resume()
                }
            } else {
                play_button.text = "PLAY"
                play_button.text = "Paused"
                musicPlayService!!.playOrPause()
//                animator.pause()
                musicPlayService!!.tag = false
            }
            if (tag2 == false) {
//                handler.post(runnable)
                tag2 = true
            }
        }

        stop_button.setOnClickListener {
            play_button.text = "PLAY"
            musicPlayService!!.stop()
//            animator.pause()
            musicPlayService!!.tag = false
        }

    }

    fun initLayout(){
        LoginService.getSongDetail(id) { status, data ->
            launch(UI){
                when(status) {
                    GetPlayListStatus.SUCCESS-> {
                        Hawk.put("data",data)
                        songDetail = Hawk.get("data")
                        songName.text = songDetail.songs?.get(0)?.name
                        singerName.text = songDetail.songs?.get(0)?.ar?.get(0)?.name
                    }
                    GetPlayListStatus.ERROR-> {
                        Toast.makeText(this@MusicPlayActivity,"ERROR", Toast.LENGTH_SHORT)
                    }
                    GetPlayListStatus.UNMATCHED-> {
                        Toast.makeText(this@MusicPlayActivity,"UNMATCHED", Toast.LENGTH_SHORT)
                    }
                }
            }
        }
    }
}