package com.lasadu.videos_sharing.login.viewmodel

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.lasadu.videos_sharing.show.ShowMessage

class LoginViewModel(application: Application) : ViewModel() {
    private lateinit var firebaseAuth: FirebaseAuth

    val showMessageLiveData = MutableLiveData<ShowMessage.NumberShowMessage>()
    val loadingProgressLiveData = MutableLiveData<ShowMessage.LoadingData>()
    val loadingSwitchScreenLiveData = MutableLiveData<ShowMessage.SwitchScreen>()

    fun loginAccountViewModel(email: String, password: String) {
        firebaseAuth = FirebaseAuth.getInstance()
        validateDataLogin(email, password)
    }
    fun forgotPassword(email: String){
        firebaseAuth = FirebaseAuth.getInstance()
        validateDateForgotPassword(email)
    }

    private fun validateDateForgotPassword(email: String) {
        if (email.isEmpty()){
            showMessageLiveData.postValue(ShowMessage.NumberShowMessage(true,"Enter Mail..."))
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            showMessageLiveData.postValue(ShowMessage.NumberShowMessage(true, "Invalid email format..."))
        }else{
            recoverPassword(email)
        }
    }

    private fun recoverPassword(email: String) {
        //Khởi tạo Dialog
        loadingProgressLiveData.postValue(ShowMessage.LoadingData(true,"Sending password reset instructions to $email"))
        firebaseAuth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                loadingProgressLiveData.postValue(ShowMessage.LoadingData(false))
                showMessageLiveData.postValue(ShowMessage.NumberShowMessage(true, "Instructions sent to \n$email"))
            }
            .addOnFailureListener {e ->
                loadingProgressLiveData.postValue(ShowMessage.LoadingData(false))
                showMessageLiveData.postValue(ShowMessage.NumberShowMessage(true, "Failed to sent due to ${e.message}"))
            }
    }

    private fun validateDataLogin(email: String, password: String) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showMessageLiveData.postValue(ShowMessage.NumberShowMessage(true, "Invalid email format..."))
        }else if (password.isEmpty()){
            showMessageLiveData.postValue(
                ShowMessage.NumberShowMessage(
                    true,
                    "Password not empty..."
                )
            )
        }else {
            loginAccount(email, password)
        }
    }

    private fun loginAccount(email: String, password: String) {
        loadingProgressLiveData.postValue(ShowMessage.LoadingData(true,"Logging In..."))
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                showMessageLiveData.postValue(
                    ShowMessage.NumberShowMessage(
                        true,
                        "Login Success..."
                    )
                )
                loadingProgressLiveData.postValue(ShowMessage.LoadingData(false))
                loadingSwitchScreenLiveData.postValue(ShowMessage.SwitchScreen(true))
            }
            .addOnFailureListener { e ->
                showMessageLiveData.postValue(
                    ShowMessage.NumberShowMessage(
                        true,
                        "Login failed due to ${e.message}..."
                    )
                )
                loadingProgressLiveData.postValue(ShowMessage.LoadingData(false))
            }
    }
}