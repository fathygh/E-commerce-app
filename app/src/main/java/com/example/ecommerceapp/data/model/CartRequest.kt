package com.example.example

import com.google.gson.annotations.SerializedName


data class CartRequest (

    @SerializedName("product_id" ) var productId : Int? = null

)