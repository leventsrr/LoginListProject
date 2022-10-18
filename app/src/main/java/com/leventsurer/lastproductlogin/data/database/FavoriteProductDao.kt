package com.leventsurer.lastproductlogin.data.database

import androidx.room.*
import com.leventsurer.lastproductlogin.data.model.ProductFavoriteStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteProductDao {
    @Query("Select * FROM favorite_product_table WHERE isFavorite = 1 ")
    fun getFavoriteProducts():Flow<List<ProductFavoriteStatus>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: ProductFavoriteStatus)

    @Update
    suspend fun update(product: ProductFavoriteStatus)

    @Delete
    suspend fun delete(product: ProductFavoriteStatus)
}