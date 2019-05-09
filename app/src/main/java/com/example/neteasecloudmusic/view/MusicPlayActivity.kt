package com.example.neteasecloudmusic.view

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import android.widget.SeekBar
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.neteasecloudmusic.R
import com.example.neteasecloudmusic.model.Status
import com.example.neteasecloudmusic.model.SongDetailBean
import com.example.neteasecloudmusic.netservice.LoginService
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.music_play_layout.*
import kotlinx.coroutines.android.UI
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class MusicPlayActivity : AppCompatActivity() {
    internal lateinit var id: String
    internal var name: String? = null
    private var artist: String? = null
    private var songDetail = SongDetailBean()
    private var musicPlayService: MusicPlayService ? = null

    private val time = SimpleDateFormat("mm:ss")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.music_play_layout)
        supportActionBar?.hide()

        val i = intent
        val b = i.extras
        if (b != null) {
            id = b.getString("id")
            name = b.getString("name")
            artist = b.getString("artist")
        }

        Hawk.init(this)
            .build()

        initLayout()
        getMusicPlayed(id)
        setSeekBarListener()
    }

    override fun onResume() {
        super.onResume()
        handler.post(runnable_seekbar)
    }

    //  回调onServiceConnected 函数，通过IBinder 获取 Service对象，实现Activity与 Service的绑定
    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            musicPlayService = (service as MusicPlayService.MyBinder).service
            handler.post(runnable)
        }

        override fun onServiceDisconnected(name: ComponentName) {
            musicPlayService = null
        }
    }

    //  通过 Handler 更新 UI 上的组件状态
    val handler = Handler()
    val runnable = Runnable {
        musicPlayService?.let {
            musicTime.setText(time.format(it?.mediaPlayer?.getCurrentPosition()))
            seek_bar?.setProgress(it!!.mediaPlayer!!.getCurrentPosition())
            seek_bar?.setMax(it!!.mediaPlayer!!.getDuration())
            musicTotal.setText(time.format(it?.mediaPlayer?.getDuration()))
        }
    }

    val runnable_seekbar = object:Runnable {
        override fun run() {
            musicPlayService?.let {
                 musicTime.setText(time.format(it?.mediaPlayer?.getCurrentPosition()))
                seek_bar.progress = it.mediaPlayer!!.currentPosition
            }
            handler.postDelayed(this, 1L)
        }
    }


    private fun PlayMusic(){
        val intent = Intent(this@MusicPlayActivity, MusicPlayService::class.java)
        val url = Hawk.get("musicurl$id", "")
        if (url != "") {
            intent.putExtra("url", url)
            startService(intent)
            bindService(intent, serviceConnection, BIND_AUTO_CREATE)
            circle_rotate_view.play()
        } else {
            Toast.makeText(this, "Something is going wrong", Toast.LENGTH_SHORT)
        }
    }

    private fun getMusicPlayed(id: String?) {
        LoginService.getMusicURL(id) { status, data ->
            launch(UI) {
                when (status) {
                    Status.SUCCESS -> {
                        Hawk.put("musicurl$id", data?.data?.get(0)?.url)
                        PlayMusic()
                        myListener()
                    }
                    Status.UNMATCHED -> Toast.makeText(this@MusicPlayActivity, "未获取歌曲详情", Toast.LENGTH_SHORT).show()
                    else -> Toast.makeText(this@MusicPlayActivity, "emmm出问题了。 $id", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun myListener(){
        play_button.setOnClickListener {
            musicPlayService?.let {
                it.playOrPause()
                when(it.tag){
                    true-> {
                        play_button.setText("PAUSED")
                        circle_rotate_view.pause()
                    }
                    false->{
                        play_button.setText("PLAYING")
                        circle_rotate_view.play()
                    }
                }
            }
        }
        setSeekBarListener()
    }

    fun setSeekBarListener(){
        seek_bar?.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    if (fromUser == true) {
                        musicPlayService?.mediaPlayer?.seekTo(seekBar.progress)
                        musicTime.setText(time.format(progress))
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar) {

                }
            })
    }


    fun initLayout(){
        LoginService.getSongDetail(id) { status, data ->
            launch(UI){
                when(status) {
                    Status.SUCCESS-> {
                        Hawk.put("data",data)
                        Hawk.put("musicpic$id", data?.songs?.get(0)?.al?.picUrl)
                        val picture = Hawk.get("musicpic$id", "")
                        Glide.with(this@MusicPlayActivity)
                            .load(picture)
                            .into(circle_rotate_view)
                        songDetail = Hawk.get("data")
                        songName.text = name
                        singerName.text = artist
                        seek_bar.getThumb().setColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_ATOP)
                        seek_bar.getProgressDrawable().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                    }
                    Status.ERROR-> {
                        Toast.makeText(this@MusicPlayActivity,"ERROR", Toast.LENGTH_SHORT)
                    }
                    Status.UNMATCHED-> {
                        Toast.makeText(this@MusicPlayActivity,"UNMATCHED", Toast.LENGTH_SHORT)
                    }
                }
            }
        }
    }
}