package com.example.firebasetest.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasetest.R
import com.example.firebasetest.ui.fragments.ListEmployeesFragment
import com.example.firebasetest.ui.fragments.ViewMapFragment
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_main_menu_colabs.*
import java.io.ByteArrayInputStream
import java.nio.charset.Charset


class MainMenuColabs : AppCompatActivity() {

    var firebaseStorage: StorageReference? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu_colabs)

        uploadDatabaseToCloud()


        instance = this

        val listFragment = ListEmployeesFragment()
        val mapFragment = ViewMapFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, listFragment)
            commit()
        }

        btnList.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, listFragment)
                commit()
            }
        }

        btnMap.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, mapFragment)
                commit()
            }
        }

    }

    fun uploadDatabaseToCloud() {

        //We can use the REST Api from cloud firebase to
        firebaseStorage = FirebaseStorage.getInstance().reference.child("employees")

        val fileReference = firebaseStorage!!.child(System.currentTimeMillis().toString() + ".txt")
        val uploadTask: StorageTask<*>
        val testText = "This is a test text for upload"
        val textBytes = ByteArrayInputStream(testText.toByteArray(Charset.defaultCharset()))
        uploadTask = fileReference.putStream(textBytes)

    }

    //To get this activity context anywhere on the app
    companion object {
        lateinit var instance: MainMenuColabs
            private set
    }

}