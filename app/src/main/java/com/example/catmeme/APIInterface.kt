package com.example.catmeme

import retrofit2.Call
import retrofit2.http.GET

interface APIInterface {
    @GET("search?limit=10?api_key=113c4afc-53d5-4d42-a856-c01f15927359")
    fun getImg(): Call<CatImg>
}