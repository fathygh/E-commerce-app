package com.example.example

import com.google.gson.annotations.SerializedName


data class DataY (

    @SerializedName("banners"  ) var banners  : ArrayList<Banners>  = arrayListOf(),
    @SerializedName("products" ) var products : ArrayList<Products> = arrayListOf(),
    @SerializedName("ad"       ) var ad       : String?             = null

)