package com.arif.generatepdffile.repos

import com.arif.generatepdffile.model.CategoriesModel
import com.arif.generatepdffile.retrofit.CategoryRetrofit

class CategoryRepository {
    suspend fun fetchCategory(type:String):CategoriesModel{
        val endUrl = "get_categories"
        return CategoryRetrofit.API.getCategory(endUrl,type)
    }
    suspend fun fetchCategory1(type:String,parentId:String):CategoriesModel{
        val endUrl = "get_categories"
        return CategoryRetrofit.API.getCategory1(endUrl,type,parentId)
    }
}