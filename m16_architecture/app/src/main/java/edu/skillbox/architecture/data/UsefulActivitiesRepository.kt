package edu.skillbox.architecture.data

import edu.skillbox.architecture.entity.UsefulActivity
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import javax.inject.Inject

private const val BASE_URL = "http://www.boredapi.com"

class UsefulActivitiesRepository @Inject constructor(){

    suspend fun getUsefulActivity(): UsefulActivity {
        return RetrofitServices.searchUsefulActivitiesApi.getUsefulActivity()
    }

    object RetrofitServices {
        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        val searchUsefulActivitiesApi: SearchUsefulActivitiesApi = retrofit.create(
            SearchUsefulActivitiesApi::class.java
        )

    }

    interface SearchUsefulActivitiesApi {
        @GET("/api/activity/")
        suspend fun getUsefulActivity(): UsefulActivityDto
    }
}