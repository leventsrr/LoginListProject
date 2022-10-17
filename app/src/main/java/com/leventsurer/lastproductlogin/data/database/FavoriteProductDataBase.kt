package com.leventsurer.lastproductlogin.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.leventsurer.lastproductlogin.data.model.FavoriteProduct
import com.leventsurer.lastproductlogin.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [FavoriteProduct::class], version = 1)
abstract class FavoriteProductDataBase :RoomDatabase(){
    abstract  fun favoriteProductDao():FavoriteProductDao

    class Callback @Inject constructor(
        private val database:Provider<FavoriteProductDataBase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ):RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val dao = database.get().favoriteProductDao()

            applicationScope.launch {
                dao.insert(FavoriteProduct(1,"Deneme",12.15,"İlk deneme","Deneme","https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",12.12))

            }
        }
    }
}