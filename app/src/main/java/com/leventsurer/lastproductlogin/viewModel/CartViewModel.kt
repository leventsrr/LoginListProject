package com.leventsurer.lastproductlogin.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leventsurer.lastproductlogin.data.model.getAllCart.Carts
import com.leventsurer.lastproductlogin.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel  @Inject constructor(private val cartRepository: CartRepository) :
    ViewModel(){

    private val _cart: MutableLiveData<List<Carts>> = MutableLiveData()
    val cart: LiveData<List<Carts>> get() = _cart

    private val _singleCart: MutableLiveData<Carts> = MutableLiveData()
    val singleCart: LiveData<Carts> get() = _singleCart
    fun getAllCart()=viewModelScope.launch {
        getAllCartSafeCall()
    }
    private suspend fun getAllCartSafeCall(){
        if (cartRepository.getCart().isSuccessful){
            _cart.value = cartRepository.getCart().body()
        }
    }

    fun getCartsLimitedViewModel(limit:Int)=viewModelScope.launch {
        getAllCartsByLimitedSafeCall(limit)
    }

    private suspend fun getAllCartsByLimitedSafeCall(limit: Int){
        if(cartRepository.getCartsLimited(limit).isSuccessful){
            _cart.value = cartRepository.getCartsLimited(limit).body()
        }
    }


    fun getSingleCart(id:Int)=viewModelScope.launch {
        getSingleCartSafeCall(id)
    }

    private suspend fun getSingleCartSafeCall(id:Int){
        if (cartRepository.getSingleCart(id).isSuccessful){
            _singleCart.value = cartRepository.getSingleCart(id).body()
        }
    }
}