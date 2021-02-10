package com.example.firebasetest.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EmployeeDao {

    @Query("SELECT * FROM EmployeeEntity")
    fun getAll(): MutableList<EmployeeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg employees: EmployeeEntity)

}