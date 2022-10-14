package com.leventsurer.lastproductlogin.api

import com.leventsurer.lastproductlogin.data.model.getAllCart.Carts
import com.leventsurer.lastproductlogin.data.model.getProductDetail.ProductDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CartApi
{

    //request for  carts to arrive
    @GET("carts")
    suspend fun getCart(): Response<List<Carts>>
    //request for carts by limited
    @GET("carts?")
    suspend fun getCartByLimited(@Query("limit") limit: Int) :Response<List<Carts>>
    //get single cart
    @GET("carts/{id}")
    suspend fun getSingleCart(@Query("id") id:Int) :Response<Carts>


}