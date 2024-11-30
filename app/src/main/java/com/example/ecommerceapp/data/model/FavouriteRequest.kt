package com.example.example

import com.google.gson.annotations.SerializedName


data class FavouriteRequest (

    @SerializedName("product_id" ) var productId : Int? = null

)