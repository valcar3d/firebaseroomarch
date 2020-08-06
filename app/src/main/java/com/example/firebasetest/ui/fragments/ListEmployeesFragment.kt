package com.example.firebasetest.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebasetest.R
import com.example.firebasetest.adapters.EmployeesAdapter
import com.example.firebasetest.db.EmployeeEntity
import com.example.firebasetest.interfaces.RecyclerViewCallback
import com.example.firebasetest.ui.MainMenuColabs
import com.example.firebasetest.ui.SingleMarkerMaps
import com.example.firebasetest.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_list_employees.*

class ListEmployeesFragment : Fragment(R.layout.fragment_list_employees), RecyclerViewCallback {

    private lateinit var userViewModel: UserViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModelObserver()
        userViewModel.getDownload()

    }

    private fun initViewModelObserver() {
        userViewModel = ViewModelProvider(MainMenuColabs.instance).get(UserViewModel::class.java)
        userViewModel.empData.observe(MainMenuColabs.instance, Observer { user ->

            val adapter = EmployeesAdapter(user, this)

            if (rvEmployeesFrag != null) {
                rvEmployeesFrag.adapter = adapter
                rvEmployeesFrag.layoutManager = LinearLayoutManager(MainMenuColabs.instance)
            }

        })

    }

    override fun onClickItem(item: EmployeeEntity) {

        var intent = Intent(MainMenuColabs.instance, SingleMarkerMaps::class.java)

        intent.putExtra("lat", item.latidude)
        intent.putExtra("log", item.longitude)
        intent.putExtra("name", item.fullName)
        startActivity(intent)
    }
}
