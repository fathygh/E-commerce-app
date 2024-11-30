package com.example.example

import com.google.gson.annotations.SerializedName


data class Productt (

    @SerializedName("id"          ) var id          : Int?    = null,
    @SerializedName("price"       ) var price       : Int?    = null,
    @SerializedName("old_price"   ) var oldPrice    : Int?    = null,
    @SerializedName("discount"    ) var discount    : Int?    = null,
    @SerializedName("image"       ) var image       : String? = null,
    @SerializedName("name"        ) var name        : String? = null,
    @SerializedName("description" ) var description : String? = null

)