import com.google.gson.annotations.SerializedName

data class FavouriteResponse (
    @SerializedName("status") var status: Boolean? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("data") var data: PageData? = PageData()
)

data class Product (
    @SerializedName("id") var id: Int? = null,
    @SerializedName("price") var price: Int? = null,
    @SerializedName("old_price") var oldPrice: Int? = null,
    @SerializedName("discount") var discount: Int? = null,
    @SerializedName("image") var image: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("description") var description: String? = null
)

data class PageData (
    @SerializedName("current_page") var currentPage: Int? = null,
    @SerializedName("data") var data: ArrayList<ProductData> = arrayListOf(),
    @SerializedName("first_page_url") var firstPageUrl: String? = null,
    @SerializedName("from") var from: Int? = null,
    @SerializedName("last_page") var lastPage: Int? = null,
    @SerializedName("last_page_url") var lastPageUrl: String? = null,
    @SerializedName("next_page_url") var nextPageUrl: String? = null,
    @SerializedName("path") var path: String? = null,
    @SerializedName("per_page") var perPage: Int? = null,
    @SerializedName("prev_page_url") var prevPageUrl: String? = null,
    @SerializedName("to") var to: Int? = null,
    @SerializedName("total") var total: Int? = null
)

data class ProductData (
    @SerializedName("id") var id: Int? = null,
    @SerializedName("product") var product: Product? = Product()
)
