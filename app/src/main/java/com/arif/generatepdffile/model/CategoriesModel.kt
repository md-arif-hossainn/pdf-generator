package com.arif.generatepdffile.model


import com.google.gson.annotations.SerializedName

data class CategoriesModel(
    @SerializedName("categories")
    val categories: List<Category>
) {
    data class Category(
        @SerializedName("child_count")
        val childCount: Int,
        @SerializedName("image")
        val image: Any,
        @SerializedName("name")
        val name: String,
        @SerializedName("parent_id")
        val parentId: Any,
        @SerializedName("parent_name")
        val parentName: Any,
        @SerializedName("pc_id")
        val pcId: String
    )
}