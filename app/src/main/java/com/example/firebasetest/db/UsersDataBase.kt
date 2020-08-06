package com.example.firebasetest.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [EmployeeEntity::class], version = 1)
abstract class UsersDataBase : RoomDatabase() {
    abstract fun userDao(): EmployeeDao

    private class UserDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var userDao = database.userDao()
                    userDao.insert(EmployeeEntity(1,"some", "1", "2","test@email.com"))
                }
            }
        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: UsersDataBase? = null

        fun getDatabase(context: Context): UsersDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UsersDataBase::class.java,
                    "EmployeesDB"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }


}