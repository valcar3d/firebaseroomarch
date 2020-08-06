package com.example.firebasetest.data
import com.example.firebasetest.interfaces.APIService
import com.example.firebasetest.interfaces.RetrofitCallback
import com.example.firebasetest.ui.model.UserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserDataSet {
    suspend fun createURL(retrofitCallBack: RetrofitCallback) {
        var incomingObject = UserData()

        //Using retrofit to do the request---------------------------------------------

        val retrofit = Retrofit.Builder()
            .baseUrl("https://dl.dropboxusercontent.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var apiService: APIService = retrofit.create(APIService::class.java)
        var call: Call<UserData> = apiService.getUserData()

        call.enqueue(object : Callback<UserData> {

            override fun onFailure(call: Call<UserData>, t: Throwable) {
                println("Error in web request ${t.message}")
            }

            override fun onResponse(call: Call<UserData>, response: Response<UserData>) {

                var responseObject: UserData? = response.body()

                if (responseObject != null) {

                    incomingObject.code = responseObject.code
                    incomingObject.data = responseObject.data
                    incomingObject.success = responseObject.success
                }

                retrofitCallBack.onComplete(incomingObject)

            }
        })
        //---------------------------------------------------------------------------------------

    }
}