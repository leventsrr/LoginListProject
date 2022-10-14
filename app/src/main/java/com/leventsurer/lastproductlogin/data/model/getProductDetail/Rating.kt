package  com.leventsurer.lastproductlogin.data.model.getProductDetail

import com.google.gson.annotations.SerializedName


data class Rating (

    @SerializedName("rate"  ) var rate  : Double? = null,
    @SerializedName("count" ) var count : Int?    = null

)