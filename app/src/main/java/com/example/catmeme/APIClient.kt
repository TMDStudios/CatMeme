package com.example.catmeme

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIClient {
    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit? {
        // we will skip the interceptor because we won't need it here
        retrofit = Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com/v1/images/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit
    }
}