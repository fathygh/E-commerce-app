import com.google.gson.annotations.SerializedName

data class AddOrRemoveFavouriteResponse (
    @SerializedName("status") var status: Boolean? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("data") var data: FavouriteData? = FavouriteData()
)

data class FavouriteData (
    @SerializedName("id") var id: Int? = null,
    @SerializedName("productt") var productt: Productt? = Productt()
)

data class Productt (
    @SerializedName("id") var id: Int? = null,
    @SerializedName("price") var price: Int? = null,
    @SerializedName("old_price") var oldPrice: Int? = null,
    @SerializedName("discount") var discount: Int? = null,
    @SerializedName("image") var image: String? = null
)
