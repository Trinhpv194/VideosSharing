package com.lasadu.videos_sharing.register.ui

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.lasadu.videos_sharing.databinding.ActivityRegisterBinding
import com.lasadu.videos_sharing.login.ui.LoginActivity
import com.lasadu.videos_sharing.register.viewmodel.RegisterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    //khai báo progress dialog
    private lateinit var progressDialog: ProgressDialog
    private val viewModel:RegisterViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.btnRegisterUser.setOnClickListener {
            val name = binding.edtUserNameRegister.text.toString()
            val email = binding.edtEmailAddressRegister.text.toString()
            val password = binding.edtPasswordRegister.text.toString()
            val confirmPassword = binding.edtConfirmPassword.text.toString()
            val avatar = binding.linkImageUser.text.toString()
            viewModel.registerAccountViewModel(name,email,password,confirmPassword,avatar)
        }
        observerLiveData()
    }

    private fun observerLiveData() {
        viewModel.showMessageLiveData.observe(this) {
            if (it.isShowMessage){
                Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.loadingProgressLiveData.observe(this){
            if(it.isShowDialog){
                //init progress dialog,will show while creating account | register User
                progressDialog = ProgressDialog(this)
                progressDialog.setTitle(it.message)
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()
            }else {
                progressDialog.dismiss()
            }
        }
        viewModel.loadingSwitchScreenLiveData.observe(this){
            if (it.isSuccessStatus){
                startActivity(Intent(this,LoginActivity::class.java))
                finish()
            }
        }
    }
}