package com.example.firebasetest.domain

import com.downloader.*
import com.example.firebasetest.data.UserDataSet
import com.example.firebasetest.db.EmployeeEntity
import com.example.firebasetest.db.UsersDataBase
import com.example.firebasetest.interfaces.RetrofitCallback
import com.example.firebasetest.ui.MainMenu
import com.example.firebasetest.ui.model.UserData
import com.example.firebasetest.util.JSONUtil.jsonToEntity
import com.example.firebasetest.util.JSONUtil.readJSONFile
import com.example.firebasetest.util.UnzipUtil.unzip
import java.text.DecimalFormat
import java.util.*


class UsersUseCase {

    private var userDataSet = UserDataSet()

    suspend fun downloadAndProcessFile() {

        userDataSet.createURL(object : RetrofitCallback {
            override fun onComplete(result: UserData?) {
                downloadURL(result)
            }
        })
    }

    private fun downloadURL(objectToGetUrl: UserData?) {
        //var dirPath = ListOfEmployees.instance.filesDir.toString()
        var dirPath = MainMenu.instance.filesDir.toString()
        var urlToDownload: String? = objectToGetUrl?.data?.file
        PRDownloader.initialize(MainMenu.instance)

        //println("Path directory to save files = $dirPath")

        var download = PRDownloader.download(urlToDownload, dirPath, "EmployeesList.zip")
            .build()
            .start(object : OnDownloadListener {
                override fun onDownloadComplete() {
                    println("Download Complete...Unziping")
                    unzip("$dirPath/EmployeesList.zip", "$dirPath/files")
                }

                override fun onError(error: Error?) {
                    println("Error while downloading ${error.toString()}")
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

        val status: Status = PRDownloader.getStatus(download)
        //println("Download status: $status")

    }

    suspend fun processJson(): MutableList<EmployeeEntity> {

        var dirPath = MainMenu.instance.filesDir.toString()

        val jsonValues = readJSONFile(dirPath, "files-employees_data.json")


        var lisOfEmployees = jsonToEntity(jsonValues)


        if (lisOfEmployees != null) {
            for (i in 0 until lisOfEmployees.size)
                UsersDataBase.getDatabase(MainMenu.instance).userDao()
                    .insert(lisOfEmployees[i])
            println("Inserted Data")
        }

        //println("Contents of Live Data = ${UsersDataBase.getDatabase(MainActivity.instance).userDao().getAll()}")

        return lisOfEmployees!!

    }

    suspend fun processNewInsertion(name: String, email: String) {

        val minLatValue = -90
        val maxLatValue = 90

        val minLogValue = -180
        val maxLogValue = 180


        val random = Random().nextDouble()
        val rLatitude = minLatValue + random * (maxLatValue - minLatValue)
        val rLongitude = minLogValue + random * (maxLogValue - minLogValue)

        val df = DecimalFormat("#.#######")

        //Assing values to new Employee object
        val randomLatitude: String = df.format(rLatitude).toString()
        val randomLongitude: String = df.format(rLongitude).toString()


        var newEmployee = EmployeeEntity(0, name, randomLatitude, randomLongitude, email)

        UsersDataBase.getDatabase(MainMenu.instance).userDao().insert(newEmployee)

    }


}


