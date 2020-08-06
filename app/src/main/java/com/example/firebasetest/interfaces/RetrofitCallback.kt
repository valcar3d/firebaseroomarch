package com.example.firebasetest.interfaces

import com.example.firebasetest.ui.model.UserData

interface RetrofitCallback {
    fun onComplete(result: UserData?)
}