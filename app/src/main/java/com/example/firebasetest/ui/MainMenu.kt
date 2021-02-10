package com.example.firebasetest.ui

import android.content.Intent
import android.os.Bundle
import android.os.storage.StorageManager
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasetest.R
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_main_menu.*

class MainMenu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        instance = this

        btnColaboradores.setOnClickListener {
            val intent = Intent(this, MainMenuColabs::class.java)
            startActivity(intent)
        }

        btnAgregarColaborador.setOnClickListener {
            val intent = Intent(this, AddEmployee::class.java)
            startActivity(intent)
        }

    }

    //To get this activity context anywhere on the app
    companion object {
        lateinit var instance: MainMenu
            private set
    }


}