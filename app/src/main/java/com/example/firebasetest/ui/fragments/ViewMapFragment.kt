package com.example.firebasetest.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.firebasetest.R
import com.example.firebasetest.ui.MainMenuColabs
import com.example.firebasetest.util.toast
import com.example.firebasetest.viewmodel.UserViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class ViewMapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var userViewModel: UserViewModel

    companion object {
        var mapFragment: SupportMapFragment? = null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        userViewModel.getCurrentEmployees()


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        var rootView = inflater.inflate(R.layout.activity_multiple_markers_map, container, false)

        mapFragment = childFragmentManager.findFragmentById(R.id.mapMultiple) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        return rootView
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap!!
        MainMenuColabs.instance.toast("Los empleados en el mundo")
        var points: LatLng


        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        userViewModel.empData.removeObservers(this)
        userViewModel.empData.observe(this, Observer { employee ->

            for (item in employee) {

                //println("Coordinates: ${item.latidude}")
                var lat = item.latidude!!.toDouble()
                var log = item.longitude!!.toDouble()

                points = LatLng(lat, log)
                mMap.addMarker(
                    MarkerOptions().position(points).title("Marker for ${item.fullName}")
                )


            }
        })
    }

}