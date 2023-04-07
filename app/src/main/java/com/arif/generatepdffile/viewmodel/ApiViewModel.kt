package com.arif.generatepdffile.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arif.generatepdffile.model.CategoriesModel
import com.arif.generatepdffile.repos.CategoryRepository
import kotlinx.coroutines.launch

class ApiViewModel:ViewModel() {
    val repository = CategoryRepository()
    val categoryData:MutableLiveData<CategoriesModel> = MutableLiveData()
    val categoryData1:MutableLiveData<CategoriesModel> = MutableLiveData()


    fun fetchCategory(type:String){
        viewModelScope.launch {
            try {
                categoryData.value = repository.fetchCategory(type)
            }catch (e:Exception){
                Log.d("ApiViewModel",e.localizedMessage)
            }
        }
    }
    fun fetchCategory1(type:String,parentId:String){
        viewModelScope.launch {
            try {
                categoryData1.value = repository.fetchCategory1(type,parentId)
            }catch (e:Exception){
                Log.d("ApiViewModel",e.localizedMessage)
            }
        }
    }
}