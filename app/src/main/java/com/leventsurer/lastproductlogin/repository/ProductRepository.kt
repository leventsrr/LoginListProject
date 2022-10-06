package com.leventsurer.lastproductlogin.repository

import com.leventsurer.lastproductlogin.api.ProductApi
import javax.inject.Inject


class ProductRepository @Inject constructor(
    private val api: ProductApi
) {

    suspend fun getProducts() = api.getAllProducts()

    suspend fun getProductsByCategory(categoryName:String) = api.getProductByCategory(categoryName)

    suspend fun getOneProduct(productId:Int) = api.getProductDetail(productId)

    suspend fun getCategories() = api.getProductCategories()
}