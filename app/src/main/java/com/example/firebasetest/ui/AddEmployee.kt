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
    private var lat: String = ""
    private var log: String = ""


    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_employee)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        val client = LocationServices.getFusedLocationProviderClient(this)

        val PERMISSION_ALL = 1
        val PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        btnCurrentLocation.setOnClickListener {

            if (!hasPermissions(this, *PERMISSIONS)) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL)
            } else {
                client.lastLocation
                    .addOnCompleteListener(this) {
                        lat = it.result?.latitude.toString()
                        log = it.result?.longitude.toString()

                        txtLatitude.text = lat
                        txtLongitude.text = log

                        MainMenuColabs.instance.toast("Se usará tu ubicación actual para el registro")
                    }
            }

        }
        btnAddColaborator.setOnClickListener {

            var name = etNameEmployee.text.toString()
            var mail = etEmail.text.toString()

            if (name == "" || mail == "") {
                toast("Porfavor rellene los campos")
            } else {
                userViewModel.insertNewUser(name, mail, lat, log)

                if (lat == "" || log == "") {
                    toast("Colaborador agregado, lat y log al azar")
                } else {
                    toast("Colaborador agregado con ubicación actual")
                }
                etNameEmployee.value = ""
                etEmail.value = ""
                txtLatitude.text = "Random Lat"
                txtLongitude.text = "Random Log"

                //Reset values for latitude and longitude
                lat = ""
                log = ""
            }

        }
        
    }

    fun hasPermissions(context: Context, vararg permissions: String): Boolean = permissions.all {
        ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }


}