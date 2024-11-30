package com.example.example

import com.google.gson.annotations.SerializedName


data class DataX (

    @SerializedName("email" ) var email : String? = null,
    @SerializedName("phone" ) var phone : String? = null,
    @SerializedName("name"  ) var name  : String? = null,
    @SerializedName("id"    ) var id    : Int?    = null,
    @SerializedName("image" ) var image : String? = null,
    @SerializedName("token" ) var token : String? = null

)