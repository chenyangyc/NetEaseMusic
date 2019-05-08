package com.example.neteasecloudmusic.view

import android.os.Bundle
import android.content.Context
import com.example.neteasecloudmusic.R
import org.jetbrains.anko.startActivity
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.example.a.zhihu.MyMusicPageAdapter
import com.example.neteasecloudmusic.model.Status
import com.example.neteasecloudmusic.model.MyPlayListBean
import com.example.neteasecloudmusic.netservice.LoginService
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.android.UI
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private val LOGOUT = 0
    var songListBean = MyPlayListBean()
    private lateinit var recyclerView: RecyclerView
    private lateinit var myMusicPageAdapter: MyMusicPageAdapter
    private lateinit var sharedPreferences : SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE)

        Hawk.init(this)
            .build()

        setLogout()
        initLayout()

    }

    fun initLayout(){
        LoginService.getPlayList(sharedPreferences.getInt("id",0)){ status, data ->
            launch(UI) {
                when(status) {
                    Status.SUCCESS-> {
                        Hawk.put("data",data)
                        songListBean = Hawk.get("data")
                        myMusicPageAdapter = MyMusicPageAdapter(this@MainActivity,songListBean)
                        recyclerView = findViewById(R.id.myMusicRecyclerView)
                        val layoutManager = LinearLayoutManager(this@MainActivity)
                        layoutManager.orientation = LinearLayoutManager.VERTICAL
                        recyclerView.layoutManager = layoutManager
                        recyclerView.adapter = myMusicPageAdapter
                    }
                    Status.ERROR-> {
                    Toast.makeText(this@MainActivity,"ERROR",Toast.LENGTH_SHORT)
                    }
                    Status.UNMATCHED-> {
                    Toast.makeText(this@MainActivity,"UNMATCHED",Toast.LENGTH_SHORT)
                    }
                }
            }
        }

    }

    /**
     * 设置一个登出的 button 的监听
     * 登出时把 loginStatus改为0，回到 welcomeActivity
     */
    fun setLogout(){
        val loginStatus = sharedPreferences.getInt("status",LOGOUT)
        cloud_button.setOnClickListener {
            LoginService.logout()
            sharedPreferences.edit().putInt("status",LOGOUT).apply()
            startActivity<WelcomeActivity>()
            finish()
        }

        if(loginStatus == LOGOUT){
            startActivity<WelcomeActivity>()
        }
    }


}
