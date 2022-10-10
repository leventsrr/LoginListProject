package com.leventsurer.lastproductlogin.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leventsurer.lastproductlogin.model.getAllCart.Carts
import com.leventsurer.lastproductlogin.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel  @Inject constructor(private val cartRepository: CartRepository) :
    ViewModel(){

    private val _cart: MutableLiveData<List<Carts>> = MutableLiveData()
    val cart: LiveData<List<Carts>> get() = _cart

    fun getAllCart()=viewModelScope.launch {
        getAllCartSafeCall()
    }
    private suspend fun getAllCartSafeCall(){
        if (cartRepository.getCart().isSuccessful){
            _cart.value = cartRepository.getCart().body()
        }
    }
}