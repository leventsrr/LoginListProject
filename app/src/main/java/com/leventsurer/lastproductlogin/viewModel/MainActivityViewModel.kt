package com.leventsurer.lastproductlogin.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leventsurer.lastproductlogin.data.model.getAllProducts.ProductItem
import com.leventsurer.lastproductlogin.data.model.getProductDetail.ProductDetail
import com.leventsurer.lastproductlogin.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val repository: ProductRepository) :
    ViewModel() {

    private val _product: MutableLiveData<List<ProductItem>> = MutableLiveData()
    val product: LiveData<List<ProductItem>> get() = _product

    private val _productDetail: MutableLiveData<ProductDetail> = MutableLiveData()
    val productDetail: LiveData<ProductDetail> get() = _productDetail

    private val _categories: MutableLiveData<ArrayList<String>> = MutableLiveData()
    val categories: LiveData<ArrayList<String>> get() = _categories

    //Get all products or products by category
    fun getProducts(categoryName: String?) = viewModelScope.launch {
        categoryName?.let {
            getProductByCategorySafeCall(it)
        } ?: run {
            getProductSafeCall()
        }
    }
    private suspend fun getProductSafeCall() {
        _product.value = repository.getProducts().body()
    }
    private suspend fun getProductByCategorySafeCall(categoryName: String) {
        if (repository.getCategories().isSuccessful){
            _product.value = repository.getProductsByCategory(categoryName).body()
        }

    }
    //Get only one product detail
    fun getProductDetail(productId: Int) = viewModelScope.launch {
        getProductDetailSafeCall(productId)
    }
    private suspend fun getProductDetailSafeCall(productId: Int) {
        if (repository.getCategories().isSuccessful){
            _productDetail.value = repository.getOneProduct(productId).body()
        }

    }
    //Get Categories
    fun getCategories() = viewModelScope.launch {
        getCategoriesSafeCall()
    }
    private suspend fun getCategoriesSafeCall() {
        if (repository.getCategories().isSuccessful){
            _categories.value = repository.getCategories().body()
        }

    }


}