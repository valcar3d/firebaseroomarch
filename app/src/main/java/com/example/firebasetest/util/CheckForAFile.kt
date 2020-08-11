package com.example.firebasetest.util

import java.io.File


object CheckForAFile {
    fun fileExists(directory: String, fileName: String): Boolean {

        val file = File(directory, fileName)
        val fileExists = file.exists()

        if (fileExists) {
            println("File found don't download")
            return true
        }
        println("File is not downloaded")
        return false
    }
}