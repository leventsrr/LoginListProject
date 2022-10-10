package com.leventsurer.lastproductlogin.api

import com.leventsurer.lastproductlogin.model.getAllProducts.ProductItem
import com.leventsurer.lastproductlogin.model.getProductDetail.ProductDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApi {

    //request for all products to arrive
    @GET("products")
    suspend fun getAllProducts(): Response<List<ProductItem>>

    //request for chosen products's detail to arrive
    @GET("products/{id}")
    suspend fun getProductDetail(@Path("id") productId: Int) :Response<ProductDetail>

    //request for chosen category's products to arrive
    @GET("products/category/{category}")
    suspend fun getProductByCategory(@Path("category") categoryName:String): Response<List<ProductItem>>

    //request for all category arrive
    @GET("products/categories")
    suspend fun getProductCategories() :Response<ArrayList<String>>


}