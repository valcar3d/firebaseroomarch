package com.example.firebasetest.domain

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.downloader.Error
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import com.downloader.PRDownloaderConfig
import com.example.firebasetest.data.UserDataSet
import com.example.firebasetest.db.EmployeeEntity
import com.example.firebasetest.db.UsersDataBase
import com.example.firebasetest.interfaces.RetrofitCallback
import com.example.firebasetest.interfaces.UnzipingComplete
import com.example.firebasetest.ui.MainMenu
import com.example.firebasetest.ui.MainMenuColabs
import com.example.firebasetest.ui.fragments.ListEmployeesFragment
import com.example.firebasetest.ui.model.UserData
import com.example.firebasetest.util.JSONUtil.jsonToEntity
import com.example.firebasetest.util.JSONUtil.readJSONFile
import com.example.firebasetest.util.UnzipUtil.unzip
import com.example.firebasetest.util.toast
import com.example.firebasetest.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_list_employees.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.util.*


class UsersUseCase {

    private var userDataSet = UserDataSet()
    private var dirPath = MainMenu.instance.filesDir.toString()
    private lateinit var userViewModel: UserViewModel
    lateinit var listEmployeesFragment: ListEmployeesFragment


    fun downloadAndProcessFile() {

        userViewModel = ViewModelProvider(MainMenuColabs.instance).get(UserViewModel::class.java)
        listEmployeesFragment = ListEmployeesFragment()

        userDataSet.createURL(object : RetrofitCallback {
            override fun onComplete(result: UserData?) {
                downloadURL(result)
            }
        })

    }

    private fun downloadURL(objectToGetUrl: UserData?) {
        var urlToDownload: String? = objectToGetUrl?.data?.file
        PRDownloader.initialize(MainMenu.instance)

        //download the file from provided URL and assing a name
        PRDownloader.download(urlToDownload, dirPath, "EmployeesList.zip")
            .build()
            .start(object : OnDownloadListener {
                override fun onDownloadComplete() {

                    unzip("$dirPath/EmployeesList.zip", "$dirPath/files",
                        object : UnzipingComplete {
                            override fun onUnzipCompletion() {
                                userViewModel.viewModelScope.launch(Dispatchers.IO) {
                                    //Process downloaded json
                                    processJson()
                                    //check the database to see all changes made
                                    userViewModel.getCurrentEmployees()

                                }

                            }
                        })

                }

                override fun onError(error: Error?) {
                    MainMenuColabs.instance.toast("Ocurrió un error en la descarga de usuarios")
                }

            })


        val config = PRDownloaderConfig.newBuilder()
            .setReadTimeout(30000)
            .setConnectTimeout(30000)
            .build()
        PRDownloader.initialize(
            MainMenu.instance,
            config
        )

    }

    fun processJson(): MutableList<EmployeeEntity> {

        var dirPath = MainMenu.instance.filesDir.toString()

        val jsonValues = readJSONFile(dirPath, "files-employees_data.json")


        var lisOfEmployees = jsonToEntity(jsonValues)


        if (lisOfEmployees != null) {
            for (i in 0 until lisOfEmployees.size)
                UsersDataBase.getDatabase(MainMenu.instance).userDao()
                    .insert(lisOfEmployees[i])
            println("JSON processed - Data inserted")
        }


        return lisOfEmployees!!

    }

    fun processNewInsertion(name: String, email: String) {

        val minLatValue = -90
        val maxLatValue = 90

        val minLogValue = -180
        val maxLogValue = 180

        //generate random values for latitude and longitude
        val random = Random().nextDouble()
        val rLatitude = minLatValue + random * (maxLatValue - minLatValue)
        val rLongitude = minLogValue + random * (maxLogValue - minLogValue)

        val df = DecimalFormat("#.#######")

        //Assing values to new Employee object
        val randomLatitude: String = df.format(rLatitude).toString()
        val randomLongitude: String = df.format(rLongitude).toString()


        //create the new object EmployeeEntity
        var newEmployee = EmployeeEntity(0, name, randomLatitude, randomLongitude, email)

        //insert the newly created object to the DB
        UsersDataBase.getDatabase(MainMenu.instance).userDao().insert(newEmployee)

    }


}


