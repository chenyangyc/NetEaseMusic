package com.example.neteasecloudmusic.view

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.example.a.zhihu.MyMusicPageAdapter
import com.example.neteasecloudmusic.R
import com.example.neteasecloudmusic.model.GetPlayListStatus
import com.example.neteasecloudmusic.model.MyPlayListBean
import com.example.neteasecloudmusic.model.PlayListDetailBean
import com.example.neteasecloudmusic.netservice.LoginService
import com.example.neteasecloudmusic.presenter.ListDetailAdapter
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.play_list_info.*
import kotlinx.coroutines.android.UI
import kotlinx.coroutines.launch


class PlayListInfoActivity : AppCompatActivity() {

    private var playListDetailBean = PlayListDetailBean()
    private lateinit var listDetailAdapter: ListDetailAdapter
    private lateinit var recyclerView: RecyclerView
    internal lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.play_list_info)
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
        LoginService.getPlayListDetail(id){status, data ->
            launch(UI) {
                when(status) {
                    GetPlayListStatus.SUCCESS-> {
                        Hawk.put("data",data)
                        playListDetailBean = Hawk.get("data")
                        listDetailAdapter = ListDetailAdapter(this@PlayListInfoActivity,playListDetailBean)
                        recyclerView = findViewById(R.id.songListRecyclerView)
                        val layoutManager = LinearLayoutManager(this@PlayListInfoActivity)
                        layoutManager.orientation = LinearLayoutManager.VERTICAL
                        recyclerView.layoutManager = layoutManager
                        recyclerView.adapter = listDetailAdapter
                    }
                    GetPlayListStatus.ERROR-> {
                        Toast.makeText(this@PlayListInfoActivity,"ERROR", Toast.LENGTH_SHORT)
                    }
                    GetPlayListStatus.UNMATCHED-> {
                        Toast.makeText(this@PlayListInfoActivity,"UNMATCHED", Toast.LENGTH_SHORT)
                    }
                }
            }
        }

    }
}