package com.leventsurer.lastproductlogin.data.database

import androidx.room.*
import com.leventsurer.lastproductlogin.data.model.FavoriteProduct
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteProductDao {
    @Query("Select * FROM favorite_product_table")
    fun getFavoriteProducts():Flow<List<FavoriteProduct>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: FavoriteProduct)

    @Update
    suspend fun update(product: FavoriteProduct)

    @Delete
    suspend fun delete(product: FavoriteProduct)
}