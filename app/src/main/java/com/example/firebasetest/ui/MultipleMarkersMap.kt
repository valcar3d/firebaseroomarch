package com.example.firebasetest.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.firebasetest.R
import com.example.firebasetest.viewmodel.UserViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


class MultipleMarkersMap : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multiple_markers_map)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        userViewModel.getCurrentEmployees()

    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        var points: LatLng

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        userViewModel.empData.observe(this, Observer { employee ->

            for (item in employee) {

                var lat = item.latidude!!.toDouble()
                var log = item.longitude!!.toDouble()

                points = LatLng(lat,log)
                mMap.addMarker(MarkerOptions().position(points).title("Marker for ${item.fullName}"))


            }
        })

    }


}