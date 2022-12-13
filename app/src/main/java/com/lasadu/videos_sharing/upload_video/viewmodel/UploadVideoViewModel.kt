package com.lasadu.videos_sharing.upload_video.viewmodel

import android.net.Uri
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lasadu.videos_sharing.application.AppApplication
import com.lasadu.videos_sharing.show.ShowMessage

class UploadVideoViewModel(application: AppApplication) :ViewModel() {
    //constants to pick video
    private val VIDEO_PICK_GALLERY_CODE = 100
    private val VIDEO_PICK_CAMERA_CODE = 101
    //constants to request camera permission to record video from camera
    private val CAMERA_REQUEST_CODE = 102
    //array for camera request permissions
    private lateinit var cameraPermissions: Array<String>
    //show thông báo
    val showMessageLiveData = MutableLiveData<ShowMessage.NumberShowMessage>()
    //show dialog
    val loadingProgressLiveData = MutableLiveData<ShowMessage.LoadingData>()


    fun uploadVideo(titleVideo:String,videoUri: Uri?){
        validateInformation(titleVideo,videoUri)
    }

    private fun validateInformation(titleVideo: String,videoUri: Uri?) {
        if (TextUtils.isEmpty(titleVideo)){
            //no title is entered
            showMessageLiveData.postValue(ShowMessage.NumberShowMessage(true,"Title is required..."))
        }else if (videoUri == null){
            //video is not picked
            showMessageLiveData.postValue(ShowMessage.NumberShowMessage(true,"Pick the video first..."))
        }else {
            //title entered,video picked ,so now  upload video
            uploadVideoToFirebase()
        }
    }

    private fun uploadVideoToFirebase() {
        //bật dialog
        loadingProgressLiveData.postValue(ShowMessage.LoadingData(true,"Upload video..."))
        //timestamp
        val timestamp = "" + System.currentTimeMillis()
    }
}