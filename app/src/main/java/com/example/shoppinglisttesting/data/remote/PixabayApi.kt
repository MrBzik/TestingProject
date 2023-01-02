package com.example.shoppinglisttesting.data.remote

import com.example.shoppinglisttesting.BuildConfig
import com.example.shoppinglisttesting.data.remote.responses.PixabayResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface PixabayApi {

    @GET("/api/")
    suspend fun makeRequest(

        @Query("key")
        key : String = BuildConfig.API_KEY,

        @Query("q")
        searchQuery : String

    ) : Response<PixabayResponse>



}