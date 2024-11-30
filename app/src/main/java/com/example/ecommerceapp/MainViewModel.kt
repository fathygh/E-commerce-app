import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.data.model.SearchRequest
import com.example.ecommerceapp.data.model.SearchResponce
import com.example.example.CartRequest
import com.example.example.FavouriteRequest
import com.example.example.HomeResponse
import com.example.example.LoginRequest
import com.example.example.LoginResponse
import com.example.example.RegisterRequest
import com.example.example.RegisterResponse
import com.example.newsapp.data.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {
    val apiService = RetrofitInstance.apiService

    // in general private variable made to deal in view model
    // and public one is made to deal in screen

    private val _isLoadingHome = MutableStateFlow(false)
    val isLoadingHome: StateFlow<Boolean> get() = _isLoadingHome


    //===========================================================================
    //login response
    private val _loginResponse = MutableStateFlow<LoginResponse?>(null)
    val loginResponse: StateFlow<LoginResponse?> get() = _loginResponse
    //login function
    fun login(loginRequest: LoginRequest, onSucess: (String) -> Unit) {
        viewModelScope.launch {
            try {
                _loginResponse.value = apiService.login(loginRequest)
                //save token in shared preference using higher order function on success
                onSucess(_loginResponse.value!!.data?.token.toString())
            } catch (e: Exception) {
                println(e.toString())
            }
        }
    }

    //=============================================================================
    //register response
    private val _registerResponse = MutableStateFlow<RegisterResponse?>(null)
    val registerResponse: StateFlow<RegisterResponse?> get() = _registerResponse
    //register function
    fun register(registerRequest: RegisterRequest, onSucess: (String) -> Unit) {
        viewModelScope.launch {
            try {
                _registerResponse.value = apiService.register(registerRequest)
                //save token in shared preference using higher order function on success
                onSucess(_registerResponse.value!!.data?.token.toString())
            } catch (e: Exception) {
                println(e.toString())
            }
        }
    }

    //=============================================================================
    //home response
    private val _homeResponse = MutableStateFlow<HomeResponse?>(null)
    val homeResponse: StateFlow<HomeResponse?> get() = _homeResponse
    //home function
    fun getHomeData(token: String) {
        viewModelScope.launch {
            _isLoadingHome.value = true
            try {
                _homeResponse.value = apiService.getHomeData(token, lang = "en")
            } catch (e: Exception) {
                println(e.toString())
            }
            finally {
                _isLoadingHome.value = false // Set loading to false
            }
        }
    }

    //=============================================================================
    //favorites response
    private val _favoritesResponse = MutableStateFlow<FavouriteResponse?>(null)
    val favoritesResponse: StateFlow<FavouriteResponse?> get() = _favoritesResponse
    // favourite function
    fun getFavorites(token: String, lang: String) {
        viewModelScope.launch {
            _isLoadingHome.value = true
            try {
                _favoritesResponse.value = apiService.getFavorites(token, lang = "en")
            } catch (e: Exception) {
                println(e.toString())
            }finally {
                _isLoadingHome.value = false // Set loading to false
            }
        }
    }


    //=============================================================================
    //add or remove from favorites response
    private val _addOrRemoveFromFavoritesResponse =
        MutableStateFlow<AddOrRemoveFavouriteResponse?>(null)
    val addOrRemoveFromFavoritesResponse: StateFlow<AddOrRemoveFavouriteResponse?> get() = _addOrRemoveFromFavoritesResponse
    // add or delete favourite function
    fun addOrRemoveFromFavorites(
        token: String,
        lang: String,
        favouriteRequest: FavouriteRequest,
        onSucess: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                _addOrRemoveFromFavoritesResponse.value =
                    apiService.addOrRemoveFromFavorites(token, lang = "en", favouriteRequest)
                onSucess(_favoritesResponse.value!!.message.toString())
            } catch (e: Exception) {
                println(e.toString())
            }
        }
    }

    //===========================================================================
//    //cart response
    private val _cartResponse = MutableStateFlow<CartResponse?>(null)
    val cartResponse: StateFlow<CartResponse?> get() = _cartResponse
    //cart function
    fun getCarts(token: String, lang: String) {
        viewModelScope.launch {
            _isLoadingHome.value = true
            try {
                _cartResponse.value = apiService.getCarts(token, lang = "en")
            } catch (e: Exception) {
                println(e.toString())
            }finally {
                _isLoadingHome.value = false // Set loading to false
            }
        }
    }
    //==========================================================================
    //add or remove from cart response
    private val _addOrRemoveFromCartResponse = MutableStateFlow<AddOrRemoveFromCartsResponse?>(null)
    val addOrRemoveFromCartResponse: StateFlow<AddOrRemoveFromCartsResponse?> get() = _addOrRemoveFromCartResponse
    // add or remove from cart function
    fun addOrRemoveFromCart(
        token: String,
        lang: String,
        cartRequest: CartRequest,
        onSucess: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                _addOrRemoveFromCartResponse.value =
                    apiService.addOrRemoveFromCarts(token, lang = "en", cartRequest)
                onSucess(_cartResponse.value!!.message.toString())

            } catch (e: Exception) {
                println(e.toString())
            }
        }
    }

    //======================================================================
    //search .....
    private val _searchResponse = MutableStateFlow<SearchResponce?>(null)
    val searchResponse: StateFlow<SearchResponce?> = _searchResponse

    fun getSearchData(token: String, lang: String, query: String) {
        viewModelScope.launch {
            _searchResponse.value = null
            try {
                    _searchResponse.value = apiService.searchProducts(token, lang, SearchRequest(query))

            } catch (e: Exception) {
                println("Exception: ${e.localizedMessage}")
            }
        }
    }
    fun clearSearchData() {
        _searchResponse.value = null
    }



}





