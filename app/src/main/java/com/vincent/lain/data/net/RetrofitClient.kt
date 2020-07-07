package com.vincent.lain.data.net

import com.vincent.lain.data.model.MenuResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    private val menusApi: MenusApi

    companion object {
        private const val API_KEY = "e2cd9fec06864daf9d0f465121919fac"
        private const val BASE_URL = "https://api.spoonacular.com"
        const val IMAGE_URL = "https://images.spoonacular.com/file/wximages/"
    }

    init {
        val builder = OkHttpClient.Builder()
        val okHttpClient = builder.build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        menusApi = retrofit.create(MenusApi::class.java)
    }

    fun searchMenus(query: String): Call<MenuResponse> {
        return menusApi.searchMenu(API_KEY, query)
    }
}