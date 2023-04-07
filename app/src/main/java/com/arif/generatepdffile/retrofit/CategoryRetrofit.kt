package com.arif.generatepdffile.retrofit

import com.arif.generatepdffile.network.ApiService
import com.arif.generatepdffile.utils.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CategoryRetrofit {
    companion object {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val API by lazy {
            retrofit.create(ApiService::class.java)
        }
    }
}