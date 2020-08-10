package com.example.firebasetest.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.firebasetest.R
import com.example.firebasetest.util.toast
import com.example.firebasetest.util.value
import com.example.firebasetest.viewmodel.UserViewModel
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_add_employee.*


class AddEmployee : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_employee)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        val client = LocationServices.getFusedLocationProviderClient(this)


        val PERMISSION_ALL = 1
        val PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION)


        btnCurrentLocation.setOnClickListener {

            if (!hasPermissions(this, *PERMISSIONS)) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL)
            } else {
                client.lastLocation
                    .addOnCompleteListener(this) {
                        toast("Latitude: ${it.result?.latitude}, Longitude: ${it.result?.longitude}")
                    }
            }

        }
        btnAddColaborator.setOnClickListener {

            var name = etNameEmployee.text.toString()
            var mail = etEmail.text.toString()

            if (name == "" || mail == "") {
                toast("Porfavor rellene los campos")
            } else {
                userViewModel.insertNewUser(name, mail)
                toast("Colaborador agregado")
                etNameEmployee.value = ""
                etEmail.value = ""
            }

        }


    }


    fun hasPermissions(context: Context, vararg permissions: String): Boolean = permissions.all {
        ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }


}