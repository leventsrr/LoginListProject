package com.leventsurer.lastproductlogin.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.leventsurer.lastproductlogin.data.database.FavoriteProductDao
import com.leventsurer.lastproductlogin.data.model.FavoriteProduct
import com.leventsurer.lastproductlogin.data.model.getProductDetail.ProductDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class FavoriteProductViewModel /*ViewModelInject*/ @Inject constructor(
    private val favoriteProductDao: FavoriteProductDao
) :ViewModel(){

    val favoriteProducts = favoriteProductDao.getFavoriteProducts().asLiveData()

    fun removeProductFromFavorites(favoriteProduct: FavoriteProduct) = viewModelScope.launch {
        favoriteProductDao.delete(favoriteProduct)
    }

    fun addProductToFavorities(favoriteProduct: FavoriteProduct) = viewModelScope.launch {
        favoriteProductDao.insert(favoriteProduct)
    }
}