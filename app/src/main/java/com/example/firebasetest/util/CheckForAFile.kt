package com.example.firebasetest.util

import java.io.File


object CheckForAFile {
    fun fileExists(directory: String, fileName: String): Boolean {

        return !(directory.isEmpty() || fileName.isEmpty())

        val file = File(directory, fileName)
        val fileExists = file.exists()

        if (fileExists) {
            //File found don't download
            return true
        }
        //File is not downloaded...download
        return false
    }
}