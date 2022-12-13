package com.lasadu.videos_sharing.splash.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.lasadu.videos_sharing.application.AppApplication
import com.lasadu.videos_sharing.show.ShowMessage
import com.lasadu.videos_sharing.util.User

class LoadUserViewModel(application: AppApplication) :ViewModel(){
    //Khai báo FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    //Thông báo chuyển màn hình
    val loadingSwitchScreenLiveData = MutableLiveData<ShowMessage.SwitchScreen>()
    fun loadUser(){
        checkUser()
    }

    private fun checkUser() {
        val firebaseAuth = firebaseAuth.currentUser
        val idMyUser =firebaseAuth?.uid
        if (idMyUser == null){
            loadingSwitchScreenLiveData.postValue(ShowMessage.SwitchScreen(true))
        }else {
            val refUsers = FirebaseDatabase.getInstance().getReference("Users")
            refUsers.child(idMyUser)
                .addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        //lấy thông tin user
                        val idUser = snapshot.child("UserID").value.toString()
                        val userName =snapshot.child("UserName").value.toString()
                        val emailAddress = snapshot.child("EmailAddress").value.toString()
                        val avatarUser = snapshot.child("AvatarUser").value.toString()
                        //
                        User(idUser,userName, emailAddress, avatarUser)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
        }
    }
}