package com.vincent.lain.data.net

import com.vincent.lain.data.model.MenuResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MenusApi {

    @GET("/food/menuItems/search")
    fun searchMenu(@Query("apiKey") apiKey: String,
                   @Query("query") q: String,
                   @Query("offset") offset: Int,
                   @Query("number") number: Int
    ): Call<MenuResponse>

}