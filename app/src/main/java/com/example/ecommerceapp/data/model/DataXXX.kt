package com.example.ecommerceapp.data.model

data class DataXXX(
    val description: String,
    val id: Int,
    val image: String,
    val images: List<String>,
    var in_cart: Boolean,
    val in_favorites: Boolean,
    val name: String,
    val price: Int
)