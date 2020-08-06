package com.example.firebasetest.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasetest.R
import com.example.firebasetest.db.EmployeeEntity
import com.example.firebasetest.interfaces.RecyclerViewCallback
import kotlinx.android.synthetic.main.users_list.view.*

class EmployeesAdapter(
    var employees: List<EmployeeEntity>,
    private var listener: RecyclerViewCallback
) :
    RecyclerView.Adapter<EmployeesAdapter.EmployeeViewHolder>() {

    inner class EmployeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    //region basic Adapter functions
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.users_list, parent, false)
        return EmployeeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return employees.size
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {

        val data = employees[position]

        holder.itemView.apply {
            empName.text = employees[position].fullName
            empLocation.text = employees[position].latidude + employees[position].longitude
            empMail.text = employees[position].email

            //Get current data of the Item selected
            setOnClickListener { listener.onClickItem(data) }
        }


    }

    //endregion


}