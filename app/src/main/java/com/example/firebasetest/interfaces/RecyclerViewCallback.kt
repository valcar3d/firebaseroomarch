package com.example.firebasetest.interfaces

import com.example.firebasetest.db.EmployeeEntity

interface RecyclerViewCallback {
    fun onClickItem(item: EmployeeEntity)
}