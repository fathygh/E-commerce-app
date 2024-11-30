package com.example.newsapp.data.network
import AddOrRemoveFavouriteResponse
import AddOrRemoveFromCartsResponse
import CartResponse

import FavouriteResponse
import com.example.ecommerceapp.data.model.SearchRequest
import com.example.ecommerceapp.data.model.SearchResponce
import com.example.example.CartRequest
import com.example.example.FavouriteRequest
import com.example.example.HomeResponse
import com.example.example.LoginRequest
import com.example.example.LoginResponse
import com.example.example.RegisterRequest
import com.example.example.RegisterResponse

import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path


interface ApiService {
    //create api endpoints
    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @POST("register")
    suspend fun register(@Body registerRequest: RegisterRequest): RegisterResponse

    @GET("home")
    suspend fun getHomeData(@Header("Authorization") token: String,@Header("lang") lang:String="en"): HomeResponse

    @GET("favorites")
    suspend fun getFavorites(@Header("Authorization") token: String,@Header("lang") lang:String="en"):FavouriteResponse

    @POST("favorites")
    suspend fun addOrRemoveFromFavorites(@Header("Authorization") token: String,@Header("lang") lang:String="en",@Body favouriteRequest: FavouriteRequest):AddOrRemoveFavouriteResponse

    @GET("carts")
    suspend fun getCarts(@Header("Authorization") token: String,@Header("lang") lang:String="en"):CartResponse

    @POST("carts")
    suspend fun addOrRemoveFromCarts(@Header("Authorization") token: String,@Header("lang") lang:String="en",@Body cartRequest: CartRequest):AddOrRemoveFromCartsResponse

    @POST("products/search")
    suspend fun searchProducts(
        @Header("Authorization")
        token: String,
        @Header("lang") lang: String,
        @Body body: SearchRequest,
    ): SearchResponce


}
object RetrofitInstance{
    //the code below allow for checking for correctness of api response in logcat
    val loggingInterceptor=HttpLoggingInterceptor().apply {
        level=HttpLoggingInterceptor.Level.BODY
    }
    val okHttpClient=okhttp3.OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        //for long time may be occur
        .callTimeout(20,java.util.concurrent.TimeUnit.SECONDS)
        .readTimeout(20,java.util.concurrent.TimeUnit.SECONDS)
        .writeTimeout(20,java.util.concurrent.TimeUnit.SECONDS)
        //
        .build()

    //create retrofit object
    val retrofit=Retrofit.Builder()
        .baseUrl("https://student.valuxapps.com/api/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    //create api service object
    val apiService= retrofit.create(ApiService::class.java)

    var token=""
}