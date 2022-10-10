package com.leventsurer.lastproductlogin.repository

import com.leventsurer.lastproductlogin.api.CartApi
import javax.inject.Inject

class CartRepository @Inject constructor(
    private val api: CartApi
) {

    suspend fun getCart() = api.getCart()
}