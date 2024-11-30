data class CartResponse(
    val status: Boolean,
    val message: String?,
    val data: CartData
)

data class CartData(
    val cart_items: List<CartItem>,
    val sub_total: Int,
    val total: Int
)

data class CartItem(
    val id: Int,
    val quantity: Int,
    val product: Producttt?
)

data class Producttt(
    val id: Int,
    val price: Int,
    val old_price: Int,
    val discount: Int,
    val image: String,
    val name: String,
    val description: String,
    val images: List<String>,
    val in_favorites: Boolean,
    val in_cart: Boolean
)
