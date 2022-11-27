package com.lasadu.videos_sharing.register.viewmodel

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.lasadu.videos_sharing.show.ShowMessage

class RegisterViewModel(application: Application) : ViewModel() {
    private lateinit var firebaseAuth: FirebaseAuth

    val showMessageLiveData = MutableLiveData<ShowMessage.NumberShowMessage>()
    val loadingProgressLiveData = MutableLiveData<ShowMessage.LoadingData>()
    val loadingSwitchScreenLiveData = MutableLiveData<ShowMessage.SwitchScreen>()

    fun registerAccountViewModel(
        name: String,
        email: String,
        password: String,
        confirmPass: String,
        avatar: String
    ) {
        firebaseAuth = FirebaseAuth.getInstance()
        //Validate Data
        validateData(name, email, password, confirmPass, avatar)
    }

    private fun validateData(
        name: String,
        email: String,
        password: String,
        confirmPass: String,
        avatar: String
    ) {
        if (name.isEmpty()) {
            showMessageLiveData.postValue(ShowMessage.NumberShowMessage(true, "Email không được để trống"))
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showMessageLiveData.postValue(ShowMessage.NumberShowMessage(true, "Email không đúng định dạng"))
        } else if (password.isEmpty()) {
            showMessageLiveData.postValue(
                ShowMessage.NumberShowMessage(
                    true,
                    "Password không được để trống"
                )
            )
        } else if (confirmPass.isEmpty()) {
            showMessageLiveData.postValue(
                ShowMessage.NumberShowMessage(
                    true,
                    "Confirm Password không được để trống"
                )
            )
        } else if (password != confirmPass) {
            showMessageLiveData.postValue(
                ShowMessage.NumberShowMessage(
                    true,
                    "Xác nhận mật khẩu không chính xác"
                )
            )
        } else if (avatar.isEmpty()) {
            showMessageLiveData.postValue(
                ShowMessage.NumberShowMessage(
                    true,
                    "Link avatar không được để trống"
                )
            )
        } else {
            createNewAccount(name, email, password, avatar)
        }

    }

    private fun createNewAccount(name: String, email: String, password: String, avatar: String) {
        loadingProgressLiveData.postValue(ShowMessage.LoadingData(true, "Creating User..."))
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                updateUserInfo(name, email, avatar)
                loadingProgressLiveData.postValue(ShowMessage.LoadingData(false))
                loadingSwitchScreenLiveData.postValue(ShowMessage.SwitchScreen(true))

            }
            .addOnFailureListener {
                loadingProgressLiveData.postValue(ShowMessage.LoadingData(false))
                showMessageLiveData.postValue(ShowMessage.NumberShowMessage(true, "Create User Error"))
            }
    }

    private fun updateUserInfo(name: String, email: String, avatar: String) {
        loadingProgressLiveData.postValue(ShowMessage.LoadingData(true, "Saving user info..."))
        //timestamp
        val timestamp = System.currentTimeMillis()
        //get current user id,since  user id register so we can get it now
        val idUser = firebaseAuth.uid
        //setup data to add in db
        val hashMap: HashMap<String, Any?> = HashMap()
        hashMap["UserID"] = idUser
        hashMap["EmailAddress"] = email
        hashMap["UserName"] = name
        hashMap["AvatarUser"] = avatar
        hashMap["Timestamp"] = timestamp
        //set data to db
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(idUser!!)
            .setValue(hashMap)
            .addOnSuccessListener {
                loadingProgressLiveData.postValue(ShowMessage.LoadingData(false))
                showMessageLiveData.postValue(ShowMessage.NumberShowMessage(true, "User created ..."))
            }
            .addOnFailureListener { e ->
                loadingProgressLiveData.postValue(ShowMessage.LoadingData(false))
                showMessageLiveData.postValue(
                    ShowMessage.NumberShowMessage(
                        true,
                        "Failed saving user info due to ${e.message}"
                    )
                )
            }
    }


}