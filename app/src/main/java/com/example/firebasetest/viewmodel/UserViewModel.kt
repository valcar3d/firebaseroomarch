package com.example.firebasetest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasetest.db.EmployeeEntity
import com.example.firebasetest.db.UsersDataBase
import com.example.firebasetest.domain.UsersUseCase
import com.example.firebasetest.ui.MainMenu
import kotlinx.coroutines.*

class UserViewModel : ViewModel() {

    private var usersUseCase = UsersUseCase()

    private var _empData = MutableLiveData<MutableList<EmployeeEntity>>()
    var empData: LiveData<MutableList<EmployeeEntity>> = _empData

    //region Core functions
    fun getDownload() {

        //Do API request in coroutine to avoid blocking the UI thread
        viewModelScope.launch(Dispatchers.IO) {
            usersUseCase.downloadAndProcessFile()
        }

    }

    fun insertNewUser(name: String, mail: String) {
        viewModelScope.launch(Dispatchers.IO) {
            usersUseCase.processNewInsertion(name, mail)
        }
    }

    fun getCurrentEmployees() {
        viewModelScope.launch(Dispatchers.IO) {
            _empData.postValue(
                UsersDataBase.getDatabase(MainMenu.instance).userDao().getAll()
            )
        }

    }
    //endregion

}