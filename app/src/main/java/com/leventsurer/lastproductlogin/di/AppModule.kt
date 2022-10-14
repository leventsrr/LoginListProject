package com.leventsurer.lastproductlogin.di

import android.app.Application
import androidx.room.Room
import com.leventsurer.lastproductlogin.api.CartApi
import com.leventsurer.lastproductlogin.api.ProductApi
import com.leventsurer.lastproductlogin.data.database.FavoriteProductDataBase
import com.leventsurer.lastproductlogin.util.constants.Cons
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
    }


    @Singleton
    @Provides
    fun provideOkHttp(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
    }


    @Singleton
    @Provides
    fun  getRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Cons.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun getRetrofitServiceInstance(retrofit:Retrofit): ProductApi {
        return retrofit.create(ProductApi::class.java)
    }

    @Singleton
    @Provides
    fun getRetrofitServiceInstanceToCart(retrofit: Retrofit):CartApi{
        return retrofit.create(CartApi::class.java)
    }

 ////////////////////////////////////////////
    @Provides
    @Singleton
    fun provideDataBase(
        app:Application,
        callback: FavoriteProductDataBase.Callback
    )= Room.databaseBuilder(app,FavoriteProductDataBase::class.java,"product_database")
        .fallbackToDestructiveMigration()
        .addCallback(callback)
        .build()

    @Provides
    fun provideFavoriteProductDao(db:FavoriteProductDataBase)= db.favoriteProductDao()

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope