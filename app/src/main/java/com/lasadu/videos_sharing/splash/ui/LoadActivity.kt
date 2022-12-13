package com.lasadu.videos_sharing.splash.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.lasadu.videos_sharing.R
import com.lasadu.videos_sharing.login.ui.LoginActivity
import com.lasadu.videos_sharing.main.ui.MainActivity
import com.lasadu.videos_sharing.splash.viewmodel.LoadUserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoadActivity : AppCompatActivity() {
    private val viewModel: LoadUserViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load)
        Handler().postDelayed(Runnable {
            viewModel.loadUser()
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        },0)
        observerLiveData()
    }

    private fun observerLiveData() {
        viewModel.loadingSwitchScreenLiveData.observe(this){
            if (it.isSuccessStatus){
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }
}