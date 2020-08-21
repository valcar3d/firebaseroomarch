package com.example.firebasetest.util

import java.io.File


object CheckForAFile {
    fun fileExists(directory: String, fileName: String): Boolean {

        if (fileName == "" || directory == "") {
            //File is not downloaded...download
            return false
        } else {

            val file = File(directory, fileName)
            val fileExists = file.exists()
            if (fileExists) {
                //File found don't download
                return true
            }
            //by default download a file
            return false
        }
    }
}