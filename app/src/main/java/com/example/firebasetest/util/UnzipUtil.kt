package com.example.firebasetest.util

import com.example.firebasetest.interfaces.UnzipingComplete
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

object UnzipUtil {

    fun unzip(_zipFile: String?, _targetLocation: String, unzipingComplete: UnzipingComplete) {

        try {
            val fin = FileInputStream(_zipFile)
            val zin = ZipInputStream(fin)
            var ze: ZipEntry?
            while (zin.nextEntry.also { ze = it } != null) {

                val fout = FileOutputStream("$_targetLocation-${ze?.name}")
                var c: Int = zin.read()
                while (c != -1) {
                    fout.write(c)
                    c = zin.read()
                }
                zin.closeEntry()
                fout.close()
            }
            zin.close()
            unzipingComplete.onUnzipCompletion()
        } catch (e: Exception) {
            println(e)
        }
    }

}