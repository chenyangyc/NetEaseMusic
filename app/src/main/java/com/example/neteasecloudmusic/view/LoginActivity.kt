package com.example.neteasecloudmusic.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.neteasecloudmusic.R
import com.example.neteasecloudmusic.model.LoginStatus
import com.example.neteasecloudmusic.model.ShareCenter
import com.example.neteasecloudmusic.netservice.LoginService
import kotlinx.android.synthetic.main.login_main.*
import org.jetbrains.anko.startActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.login_main)
        back_button.setOnClickListener {
            finish()
        }
        login_main_button.setOnClickListener {
            if (loginPhoneNum.text.toString() == "") {
                Toast.makeText(this, "请输入电话号码", Toast.LENGTH_SHORT).show()
            } else {
                LoginService.login(loginPhoneNum.text.toString(), loginPassword.text.toString()) { status, data ->
                    runOnUiThread {
                        when (status) {
                            LoginStatus.SUCCESS -> {
                                Toast.makeText(this, "${data!!.profile?.nickname}，登陆成功", Toast.LENGTH_SHORT).show()
                                val share = getSharedPreferences("data", Context.MODE_PRIVATE)
                                ShareCenter.updateShareInfo(
                                    share,
                                    data,
                                    loginPhoneNum.text.toString(),
                                    loginPassword.text.toString()
                                )
                                startActivity<MainActivity>()
                                finish()
                            }
                            LoginStatus.WRONGPASSWORD -> Toast.makeText(
                                this,
                                "密码错误",
                                Toast.LENGTH_SHORT
                            ).show()
                            LoginStatus.ERROR ->
                                Toast.makeText(
                                    this,
                                    "Maybe no Internet ? ",
                                    Toast.LENGTH_SHORT
                                ).show()
                        }
                    }
                }
            }
        }
    }
}
