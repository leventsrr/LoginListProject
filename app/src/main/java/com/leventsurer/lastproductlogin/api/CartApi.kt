package com.leventsurer.lastproductlogin.api

import com.leventsurer.lastproductlogin.model.getAllCart.Carts
import retrofit2.Response
import retrofit2.http.GET

interface CartApi
{

    //request for only one cart to arrive
    @GET("carts")
    suspend fun getCart(): Response<List<Carts>>
}