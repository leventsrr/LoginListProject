package com.leventsurer.lastproductlogin.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.leventsurer.lastproductlogin.data.model.ProductFavoriteStatus
import com.leventsurer.lastproductlogin.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [ProductFavoriteStatus::class], version = 1)
abstract class FavoriteProductDataBase :RoomDatabase(){
    abstract  fun favoriteProductDao():FavoriteProductDao

    class Callback @Inject constructor(
        private val database:Provider<FavoriteProductDataBase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ):RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val dao = database.get().favoriteProductDao()

            /*applicationScope.launch {
                dao.insert(ProductFavoriteStatus(1,"Deneme",12.15,"İlk deneme","Deneme","https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",12.12))

            }*/
        }
    }
}