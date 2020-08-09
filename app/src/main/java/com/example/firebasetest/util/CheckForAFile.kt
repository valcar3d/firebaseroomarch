package com.example.firebasetest.util

import android.content.Context
import java.io.File

object CheckForAFile {
    fun fileExists(context: Context, filename: String?): Boolean {
        val file: File = context.getFileStreamPath(filename)
        return !(file == null || !file.exists())
    }
}