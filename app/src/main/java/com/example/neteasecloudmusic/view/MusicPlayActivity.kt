package com.example.neteasecloudmusic.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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