package com.arif.generatepdffile.network

import com.arif.generatepdffile.model.CategoriesModel
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {
    @Headers("Accept: application/json")
    @GET
    suspend fun getCategory(
        @Url endUrl: String,
        @Query("type") type:String
    ): CategoriesModel

    @Headers("Accept: application/json")
    @GET
    suspend fun getCategory1(
        @Url endUrl: String,
        @Query("type") type:String,
        @Query("parent_id") parentId:String
    ): CategoriesModel
}