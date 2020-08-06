package com.example.firebasetest.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "EmployeeEntity")
class EmployeeEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "name") val fullName: String?,
    @ColumnInfo(name = "lat") val latidude: String?,
    @ColumnInfo(name = "log") val longitude: String?,
    @ColumnInfo(name = "mail") val email: String?
)





