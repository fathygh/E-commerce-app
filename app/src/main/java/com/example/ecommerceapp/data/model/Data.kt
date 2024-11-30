package com.example.example

import com.google.gson.annotations.SerializedName


data class Data (

    @SerializedName("id"     ) var id     : Int?    = null,
    @SerializedName("name"   ) var name   : String? = null,
    @SerializedName("email"  ) var email  : String? = null,
    @SerializedName("phone"  ) var phone  : String? = null,
    @SerializedName("image"  ) var image  : String? = null,
    @SerializedName("points" ) var points : Int?    = null,
    @SerializedName("credit" ) var credit : Int?    = null,
    @SerializedName("token"  ) var token  : String? = null

)
