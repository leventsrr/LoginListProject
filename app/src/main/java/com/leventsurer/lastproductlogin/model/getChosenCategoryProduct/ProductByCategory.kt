package com.leventsurer.lastproductlogin.model.getChosenCategoryProduct

import com.google.gson.annotations.SerializedName

//class used for the response of the request from which all products by category are called
data class ProductByCategory (

    @SerializedName("id"          ) var id          : Int?    = null,
    @SerializedName("title"       ) var title       : String? = null,
    @SerializedName("price"       ) var price       : Int?    = null,
    @SerializedName("description" ) var description : String? = null,
    @SerializedName("category"    ) var category    : String? = null,
    @SerializedName("image"       ) var image       : String? = null,
    @SerializedName("rating"      ) var rating      : Rating? = Rating()

)