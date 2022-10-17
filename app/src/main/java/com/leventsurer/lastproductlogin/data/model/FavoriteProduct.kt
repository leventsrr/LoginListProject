package com.leventsurer.lastproductlogin.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "favorite_product_table")
@Parcelize
data class FavoriteProduct(
    val id:Int,
    val title:String,
    val price:Double,
    val description:String,
    val category:String,
    val image:String,
    val rating:Double,
    @PrimaryKey(autoGenerate = true) val primaryId: Int = 0
    ) :Parcelable


