package com.leventsurer.lastproductlogin.data.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class FavoriteProduct(
    val id:Int,
    val title:String,
    val price:Double,
    val description:String,
    val category:String,
    val image:String,
    val rating:Double,
    ) :Parcelable


