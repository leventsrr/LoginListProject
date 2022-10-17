package com.leventsurer.lastproductlogin.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.leventsurer.lastproductlogin.data.database.FavoriteProductDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class FavoriteProductViewModel /*ViewModelInject*/ @Inject constructor(
    private val favoriteProductDao: FavoriteProductDao
) :ViewModel(){

    val favoriteProducts = favoriteProductDao.getFavoriteProducts().asLiveData()
}