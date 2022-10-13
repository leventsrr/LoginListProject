package com.leventsurer.lastproductlogin.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.leventsurer.lastproductlogin.databinding.FragmentCartDetailBinding
import com.leventsurer.lastproductlogin.model.getAllCart.Carts
import com.leventsurer.lastproductlogin.model.getProductDetail.ProductDetail
import com.leventsurer.lastproductlogin.util.adapters.CartDetailAdapter
import com.leventsurer.lastproductlogin.viewModel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.ParseException
import java.text.SimpleDateFormat

@AndroidEntryPoint
class CartDetailFragment : Fragment() {
    private var _binding: FragmentCartDetailBinding? = null
    private val binding: FragmentCartDetailBinding get() = _binding!!

    private var adapterList = ArrayList<ProductDetail>()
    private lateinit var adapter: CartDetailAdapter
    private val mainActivityViewModel: MainActivityViewModel by viewModels()
    private var totalPrice = 0.0
    private lateinit var chosenCart: Carts
    private var quantityList=ArrayList<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleArguments()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    @Throws(ParseException::class)
    //Parse the data format to ["Date","Time"]
    private fun modifyDateLayout(inputDate: String): List<String> {

        val allDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(inputDate)
        val parsedDate = allDate?.let { SimpleDateFormat("dd.MM.yyyy HH:mm").format(it) }
        return parsedDate!!.split(" ")
    }

    override  fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleOnClickEvents()

        setupAdapter()
        getProductsQuantity()
        getProductsDetail()

        subscribeObserve()
        valueBind()
    }

    var i = 0
    //private var modelList=ArrayList<CartDetailProductInfo>()
    private fun subscribeObserve() {

        mainActivityViewModel.productDetail.observe(viewLifecycleOwner) {

            adapterList.add(it)
            Log.e("listtt","${it.id}")

            val sortedList = sortIncreasingById(adapterList)
            adapter.list = sortedList
        }


    }
    private fun sortIncreasingById(originalArray: ArrayList<ProductDetail>):ArrayList<ProductDetail> {
        val sortingArray = originalArray.sortedWith(compareBy { it.id })
        val sortedArray = ArrayList<ProductDetail>()

        for (product: ProductDetail in sortingArray) {

            sortedArray.add(product)
        }
        return sortedArray
    }
    private fun getProductsDetail()  {
        for (product in chosenCart.products) {
            Log.e("product","productId:${product.productId}")

                 mainActivityViewModel.getProductDetail(product.productId!!)
        }
    }

    private fun handleArguments() {
        arguments?.let {
            chosenCart = CartDetailFragmentArgs.fromBundle(it).cart
        }

    }

    private fun valueBind() {
        binding.cartDetailDate.text = modifyDateLayout(chosenCart.date!!)[0]
        binding.cartDetailTime.text = modifyDateLayout(chosenCart.date!!)[1]
        binding.cartDetailTotalPrice.text = totalPrice.toString()
    }

    private fun setupAdapter() {
        binding.cartDetailList.layoutManager = LinearLayoutManager(context)
        adapter = CartDetailAdapter()
        binding.cartDetailList.adapter = adapter
    }

    private fun getProductsQuantity(){
        for(quantity in chosenCart.products){
            quantity.quantity?.let { quantityList.add(it) }
        }
        adapter.quantityList = quantityList
    }
    private fun handleOnClickEvents() {
        binding.apply {
            backToCartListButton.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

}