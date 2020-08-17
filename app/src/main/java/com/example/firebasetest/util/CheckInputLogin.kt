package com.example.firebasetest.util

object CheckInputLogin {
    fun validateRegistratonInput(
        userName: String,
        password: String
    ): Boolean {

        if (userName.isEmpty() || password.isEmpty()) {
            return false
        }
        return !(userName == null || password == null)
    }
}