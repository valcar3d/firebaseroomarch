package com.example.firebasetest.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasetest.R
import com.example.firebasetest.util.toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        btnLogin.setOnClickListener {
            attempLogin()
        }

    }

    private fun attempLogin() {

        if (username.text.toString() == "" || password.text.toString() == "") {
            toast("Porfavor inserta datos para login")
        } else {


            var uName = username.text.toString()
            var uPass = password.text.toString()


            mAuth.signInWithEmailAndPassword(uName, uPass)
                .addOnCompleteListener(
                    this
                ) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        //val user = mAuth.currentUser

                        val intent = Intent(this, MainMenu::class.java)
                        startActivity(intent)
                        finish()

                    } else {
                        // If sign in fails, display a message to the user.
                        toast("Credenciales no válidas")
                    }

                }
        }
    }


}