package com.leventsurer.lastproductlogin.model.getAllCart

import com.google.gson.annotations.SerializedName


data class Products (

    @SerializedName("productId" ) var productId : Int? = null,
    @SerializedName("quantity"  ) var quantity  : Int? = null

)