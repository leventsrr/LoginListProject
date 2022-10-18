package com.leventsurer.lastproductlogin.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.leventsurer.lastproductlogin.R
import com.leventsurer.lastproductlogin.databinding.FragmentCartBinding
import com.leventsurer.lastproductlogin.data.model.getAllCart.Carts
import com.leventsurer.lastproductlogin.data.model.getAllCart.Products
import com.leventsurer.lastproductlogin.data.model.getAllProducts.ProductItem
import com.leventsurer.lastproductlogin.util.adapters.CartAdapter
import com.leventsurer.lastproductlogin.viewModel.CartViewModel
import com.leventsurer.lastproductlogin.viewModel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class CartFragment : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private val binding: FragmentCartBinding get() = _binding!!
    private val cartViewModel: CartViewModel by viewModels()
    private var adapterList = ArrayList<Carts>()
    private lateinit var adapter: CartAdapter



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
        sortCarts()
    }
    //filters shopping carts by desired number
    private fun sortCarts() {
        binding.apply {
            binding.limitCartsButton.setOnClickListener {
                val dialog = BottomSheetDialog(requireContext())
                val view = layoutInflater.inflate(R.layout.bottom_sheet_dialog_to_limit_cart, null)
                dialog.setCancelable(false)
                dialog.setContentView(view)
                dialog.show()
                val lastCartButton = view.findViewById<Button>(R.id.lastCart)
                val lastThreeCartsButton = view.findViewById<Button>(R.id.lastThreeCarts)
                val lastFiveCartsButton = view.findViewById<Button>(R.id.lastFiveCarts)


                lastCartButton.setOnClickListener {
                    cartViewModel.getCartsLimitedViewModel(1)
                    dialog.dismiss()
                }


                lastThreeCartsButton.setOnClickListener {
                    cartViewModel.getCartsLimitedViewModel(3)
                    dialog.dismiss()
                }
                lastFiveCartsButton.setOnClickListener {
                    cartViewModel.getCartsLimitedViewModel(5)
                    dialog.dismiss()
                }
            }
        }
    }
    //listens for the information of the shopping cart
    private  fun subscribeObserve() {
        cartViewModel.cart.observe(viewLifecycleOwner) { response ->
            adapterList.clear()
            adapterList.addAll(response)
            adapter.list = adapterList
        }
    }
    //Connects with adapter class
    private fun setupAdapter() {
        binding.cartsList.layoutManager = LinearLayoutManager(context)
        adapter = CartAdapter()
        binding.cartsList.adapter = adapter
        adapter.setOnClickListenerCustom {cart ->
            goToCartDetailFragment(cart)
        }
    }
    //pass to cart detail fragment
    private fun goToCartDetailFragment(cart: Carts) {
        val action = CartFragmentDirections.actionCartFragmentToCartDetailFragment(cart)
        findNavController().navigate(action)
    }
    //The request that calls the carts is discarded
    private fun cartRequest() {
        cartViewModel.getAllCart()
    }

}