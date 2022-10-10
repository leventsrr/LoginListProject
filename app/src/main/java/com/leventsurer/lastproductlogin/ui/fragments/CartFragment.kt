package com.leventsurer.lastproductlogin.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.leventsurer.lastproductlogin.databinding.FragmentCartBinding
import com.leventsurer.lastproductlogin.model.getAllCart.Carts
import com.leventsurer.lastproductlogin.model.getAllCart.PriceModel
import com.leventsurer.lastproductlogin.util.adapters.CartAdapter
import com.leventsurer.lastproductlogin.viewModel.CartViewModel
import com.leventsurer.lastproductlogin.viewModel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private val binding: FragmentCartBinding get() = _binding!!
    private val cartViewModel: CartViewModel by viewModels()
    private val mainActivityViewModel: MainActivityViewModel by viewModels()
    private var adapterList = ArrayList<Carts>()
    private lateinit var adapter: CartAdapter
    val cartTotalPriceHashMap = HashMap<Int, Double>()
    var cartCounter = 0
    var productCounter = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cartRequest()
        setupAdapter()
        subscribeObserve()

    }

    private fun subscribeObserve() {

        cartViewModel.cart.observe(viewLifecycleOwner) { carts ->
            adapterList.addAll(carts)
            if (adapterList.isNotEmpty()){
                if (adapterList[cartCounter].products.isNotEmpty()){
                    mainActivityViewModel.getProductDetail(adapterList[cartCounter].products[productCounter].productId!!)
                }
            }
        }

        mainActivityViewModel.productDetail.observe(viewLifecycleOwner) { productDetail ->
            val currentProduct = adapterList[cartCounter].products[productCounter]
            val currentCart = adapterList[cartCounter]

            val price = productDetail.price!! * currentProduct.quantity!!
            Log.e("TAG", "subscribeObserve: sepet id :${currentCart.id}  productId : ${currentProduct.productId} price : ${productDetail.price} quantity : ${currentProduct.quantity!!}"  )

            if (cartTotalPriceHashMap[currentCart.id!!] == null) {
                cartTotalPriceHashMap[currentCart.id!!] = 0.0
            }
            cartTotalPriceHashMap[currentCart.id!!] = cartTotalPriceHashMap[currentCart.id!!]!! + price

            adapterList[cartCounter].totalPrice = cartTotalPriceHashMap[currentCart.id!!]!!
            Log.e("TAG", "subscribeObserve:  sepet id :${currentCart.id}  sepet total price : ${ currentCart.totalPrice} ")

            productCounter++
            if (adapterList[cartCounter].products.size > productCounter){
                mainActivityViewModel.getProductDetail(adapterList[cartCounter].products[productCounter].productId!!)
            }else {
                cartCounter++
                productCounter = 0
                if (adapterList.size > cartCounter){
                    mainActivityViewModel.getProductDetail(adapterList[cartCounter].products[productCounter].productId!!)
                }else {
                    Log.e("TAG", "subscribeObserve: Daha ne istiyon vicdansız! " )
                }
            }

            adapter.list = adapterList
        }
    }


    private fun setupAdapter() {
        binding.cartsList.layoutManager = LinearLayoutManager(context)
        adapter = CartAdapter()
        binding.cartsList.adapter = adapter

    }

    private fun cartRequest() {
        cartViewModel.getAllCart()
    }

}