package com.leventsurer.lastproductlogin.viewModel

import androidx.lifecycle.ViewModel
import com.leventsurer.lastproductlogin.data.model.FavoriteProduct
import javax.inject.Inject

class FavoriteProductViewModel /*ViewModelInject*/ @Inject constructor(
    private val favoriteProduct: FavoriteProduct
) :ViewModel(){
}