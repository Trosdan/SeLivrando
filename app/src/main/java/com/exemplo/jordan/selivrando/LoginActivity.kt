package com.exemplo.jordan.selivrando

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }


    public fun btn_Login(view: View){
        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
    }
}
