package com.lasadu.videos_sharing.login.ui

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.lasadu.videos_sharing.R
import com.lasadu.videos_sharing.databinding.ActivityForgotPasswordBinding
import com.lasadu.videos_sharing.databinding.ActivityLoginBinding
import com.lasadu.videos_sharing.login.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForgotPasswordActivity : AppCompatActivity() {
    //ViewBinding
    private lateinit var binding : ActivityForgotPasswordBinding
    //khai báo progressDialog
    private lateinit var progressDialog: ProgressDialog
    //viewModel
    private val viewModel: LoginViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //khởi tạo binding
        binding = ActivityForgotPasswordBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.btnForgotPass.setOnClickListener {
            val emailAddress = binding.edtEmail.text.toString().trim()
            viewModel.forgotPassword(emailAddress)
        }
        binding.imgBack.setOnClickListener {
            onBackPressed()
        }
        observerLiveData()
    }

    private fun observerLiveData() {
        viewModel.showMessageLiveData.observe(this){
            if (it.isShowMessage){
                Toast.makeText(this,it.message, Toast.LENGTH_SHORT).show()
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
    }
}