package edu.skillbox.nasa.api

import edu.skillbox.nasa.models.Photos
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface SearchMarsPhotosApi {
    @GET("/api/v1/rovers/curiosity/photos")
    suspend fun getPhotosInfoList(
        @Query("api_key") api_key: String = "zspHzSkW9wgEltSBinicaIj2tWD1fRdhOsdsbnPS"
    ): Photos
}

val retrofit = Retrofit
    .Builder()
    .client(
        OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also {
        it.level = HttpLoggingInterceptor.Level.BODY
    }).build()
    )
    .baseUrl("https://api.nasa.gov/mars-photos/")
    .addConverterFactory(MoshiConverterFactory.create())
    .build()
    .create(SearchMarsPhotosApi::class.java)
