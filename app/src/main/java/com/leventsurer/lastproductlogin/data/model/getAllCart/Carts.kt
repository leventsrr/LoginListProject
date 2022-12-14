package com.leventsurer.lastproductlogin.data.model.getAllCart



import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Carts(

    @SerializedName("id"       ) var id: Int?                = null,
    @SerializedName("userId"   ) var userId: Int?                = null,
    @SerializedName("date"     ) var date: String?             = null,
    @SerializedName("products" ) var products:@RawValue ArrayList<Products>  = arrayListOf(),
    @SerializedName("__v"      ) var _v: Int?                = null,



    ): Parcelable