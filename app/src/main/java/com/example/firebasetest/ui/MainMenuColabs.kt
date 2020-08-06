package com.example.firebasetest.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasetest.R
import com.example.firebasetest.ui.fragments.ListEmployeesFragment
import com.example.firebasetest.ui.fragments.ViewMapFragment
import kotlinx.android.synthetic.main.activity_main_menu_colabs.*

class MainMenuColabs : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu_colabs)

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

    //To get this activity context anywhere on the app
    companion object {
        lateinit var instance: MainMenuColabs
            private set
    }
}