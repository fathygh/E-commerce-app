data class AddOrRemoveFromCartsResponse(
    val status: Boolean,
    val message: String,
    val data: CartDataa
)

data class CartDataa(
    val cartItem: CartItemm
)

data class CartItemm(
    val id: Int,
    val quantity: Int,
    val product: Productn
)

data class Productn(
    val id: Int,
    val price: Double,
    val oldPrice: Double,
    val discount: Int,
    val image: String,
    val name: String,
    val description: String
)
