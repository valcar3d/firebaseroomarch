package com.example.firebasetest.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.firebasetest.R
import com.example.firebasetest.util.toast
import com.example.firebasetest.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_add_employee.*

class AddEmployee : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_employee)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        btnAddColaborator.setOnClickListener {

            var name = etNameEmployee.text.toString()
            var mail = etEmail.text.toString()

            if (name == "" || mail == "") {
                toast("Porfavor rellene los campos")
            } else {
                userViewModel.insertNewUser(name, mail)
                toast("Colaborador agregado")
            }

        }
    }


}