package com.lasadu.videos_sharing.show

class ShowMessage {
    data class NumberShowMessage(val isShowMessage: Boolean, val message: String? = "haha")
    data class LoadingData(val isShowDialog: Boolean, val message: String? = "haha")
    data class SwitchScreen(val isSuccessStatus : Boolean)
}