package com.leventsurer.lastproductlogin.model.getAllCart



import com.google.gson.annotations.SerializedName


data class Carts(

    @SerializedName("id"       ) var id: Int?                = null,
    @SerializedName("userId"   ) var userId: Int?                = null,
    @SerializedName("date"     ) var date: String?             = null,
    @SerializedName("products" ) var products: ArrayList<Products> = arrayListOf(),
    @SerializedName("__v"      ) var _v: Int?                = null,
    var priceList : ArrayList<PriceModel> = arrayListOf(),
    var totalPrice: Double? = 2.0


)