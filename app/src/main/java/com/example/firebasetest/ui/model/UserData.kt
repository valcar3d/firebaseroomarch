package com.example.firebasetest.ui.model

import com.google.gson.annotations.SerializedName

data class UserData(
    @field:SerializedName("code")
    var code: Int? = null,

    @field:SerializedName("data")
    var data: Data? = null,

    @field:SerializedName("success")
    var success: Boolean? = null
)

data class Data(
    @field:SerializedName("file")
    val file: String? = null
)