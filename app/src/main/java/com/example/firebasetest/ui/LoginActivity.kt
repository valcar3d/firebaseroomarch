package com.example.firebasetest.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.firebasetest.R
import com.example.firebasetest.databinding.ActivityLoginBinding
import com.example.firebasetest.util.CheckInputLogin
import com.example.firebasetest.util.toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {
            attempLogin()
        }

    }

    private fun attempLogin() {

        val username = binding.username.text.toString()
        val password = binding.password.text.toString()

        if (!CheckInputLogin.validateRegistratonInput(username,password))

        { toast("Porfavor completa los campos para login") } else {

            mAuth.signInWithEmailAndPassword(username, password)
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