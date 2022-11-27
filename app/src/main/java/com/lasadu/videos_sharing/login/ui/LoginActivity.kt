package com.lasadu.videos_sharing.login.ui

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.lasadu.videos_sharing.databinding.ActivityLoginBinding
import com.lasadu.videos_sharing.login.viewmodel.LoginViewModel
import com.lasadu.videos_sharing.main.ui.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {
    //khai báo viewBinding
    private lateinit var binding: ActivityLoginBinding
    //khai báo progressDialog
    private lateinit var progressDialog: ProgressDialog
    //viewModel
    private val viewModel:LoginViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //khởi tạo binding
        binding = ActivityLoginBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.btnLoginUser.setOnClickListener {
            val emailAddress = binding.edtEmail.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()
            viewModel.loginAccountViewModel(emailAddress,password)
        }
        observerLiveData()
    }
    private fun observerLiveData() {
        viewModel.showMessageLiveData.observe(this){
            if (it.isShowMessage){
                Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.loadingProgressLiveData.observe(this){
            if (it.isShowDialog){
                progressDialog = ProgressDialog(this)
                progressDialog.setTitle(it.message)
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()
            }else{
                progressDialog.dismiss()
            }
        }
        viewModel.loadingSwitchScreenLiveData.observe(this){
            if (it.isSuccessStatus){
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
        }
    }
}