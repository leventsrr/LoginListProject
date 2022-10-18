package com.leventsurer.lastproductlogin.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.leventsurer.lastproductlogin.data.database.FavoriteProductDao
import com.leventsurer.lastproductlogin.data.model.ProductFavoriteStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class FavoriteProductViewModel /*ViewModelInject*/ @Inject constructor(
    private val favoriteProductDao: FavoriteProductDao
) :ViewModel(){

    val favoriteProducts = favoriteProductDao.getFavoriteProducts().asLiveData()

    fun removeProductFromFavorites(productFavoriteStatus: ProductFavoriteStatus) = viewModelScope.launch {
        favoriteProductDao.delete(productFavoriteStatus)
    }

    fun addProductToFavorities(productFavoriteStatus: ProductFavoriteStatus) = viewModelScope.launch {
        favoriteProductDao.insert(productFavoriteStatus)
    }

    fun updateProductFavoriteStatus(productFavoriteStatus: ProductFavoriteStatus) = viewModelScope.launch {
        favoriteProductDao.update(productFavoriteStatus)
    }
}