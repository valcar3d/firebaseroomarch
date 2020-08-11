package com.example.firebasetest.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasetest.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.common.primitives.Doubles

class SingleMarkerMaps : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var lat: String? = ""
    private var log: String? = ""
    private var fullName: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //Getting information of selected name in the list
        lat = intent.getStringExtra("lat")
        log = intent.getStringExtra("log")
        fullName = intent.getStringExtra("name")

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        //Add a marker in selected user and move the camera
        val selectedName = LatLng(Doubles.tryParse(lat)!!, Doubles.tryParse(log)!!)
        mMap.addMarker(
            MarkerOptions()
                .position(selectedName)
                .title("Marker for $fullName")
                .snippet("This is employee information"))


        mMap.moveCamera(CameraUpdateFactory.newLatLng(selectedName))
        mMap.animateCamera(CameraUpdateFactory.zoomTo(20f))

    }


}