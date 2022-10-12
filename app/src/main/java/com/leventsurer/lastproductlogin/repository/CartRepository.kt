package com.leventsurer.lastproductlogin.repository

import com.leventsurer.lastproductlogin.api.CartApi
import javax.inject.Inject

class CartRepository @Inject constructor(
    private val api: CartApi
) {

    suspend fun getCart() = api.getCart()

    suspend fun getCartsLimited(limit:Int) = api.getCartByLimited(limit)


    suspend fun getSingleCart(id:Int) = api.getSingleCart(id)
}