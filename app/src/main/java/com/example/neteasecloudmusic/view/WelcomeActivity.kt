package com.example.neteasecloudmusic.view

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.neteasecloudmusic.R
import kotlinx.android.synthetic.main.login.*
import org.jetbrains.anko.startActivity

@SuppressLint("Registered")
class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.login)
        login_button.setOnClickListener {
            startActivity<LoginActivity>()
        }
    }
}
