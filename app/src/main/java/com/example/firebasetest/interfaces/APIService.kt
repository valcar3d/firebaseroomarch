package com.example.firebasetest.interfaces

import com.example.firebasetest.ui.model.UserData
import retrofit2.Call
import retrofit2.http.GET

interface APIService {

    @GET("/s/5u21281sca8gj94/getFile.json?dl=0")
    fun getUserData(): Call<UserData>
}