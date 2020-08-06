package com.example.firebasetest.util

import com.example.firebasetest.db.EmployeeEntity
import org.json.JSONObject
import java.io.File
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.nio.charset.Charset

object JSONUtil {

    fun readJSONFile(directory: String, fileName: String): String {

        if (directory == "" || fileName == "") {
            println("No such directory and file not found")
            return "ERROR"
        }

        val yourFile = File(directory, fileName)
        val stream = FileInputStream(yourFile)
        var jString: String?
        jString = stream.use { stream ->
            val fc: FileChannel = stream.channel
            val bb: MappedByteBuffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size())
            Charset.defaultCharset().decode(bb).toString()
        }

        return jString
    }

    fun jsonToEntity(jsonStringToConvert: String): ArrayList<EmployeeEntity>? {


        val rootJsonObject = JSONObject(jsonStringToConvert)
        val dataObject = rootJsonObject.getJSONObject("data")
        val employeesArray = dataObject.getJSONArray("employees")

        var listOfEmployeeEntity: ArrayList<EmployeeEntity>? = ArrayList()


        for (i in 0 until employeesArray.length()) {
            val item = employeesArray.getJSONObject(i)

            listOfEmployeeEntity?.add(
                EmployeeEntity(
                    item.getInt("id"),
                    item.get("name") as String,
                    item.getJSONObject("location").getString("lat"),
                    item.getJSONObject("location").getString("log"),
                    item.getString("mail")
                )
            )
        }

        return listOfEmployeeEntity
    }

}