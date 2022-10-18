package com.leventsurer.lastproductlogin.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.leventsurer.lastproductlogin.databinding.FragmentCartDetailBinding
import com.leventsurer.lastproductlogin.data.model.getAllCart.Carts
import com.leventsurer.lastproductlogin.data.model.getProductDetail.ProductDetail
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


    override  fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleOnClickEvents()
        setupAdapter()
        getProductsQuantity()
        getProductsDetail()
        subscribeObserve()
        valueBind()
    }

    //listens to product detail information
    private fun subscribeObserve() {

        mainActivityViewModel.productDetail.observe(viewLifecycleOwner, Observer {

            adapterList.add(it)
            Log.e("listtt","${it.id}")


            val sortedList = sortIncreasingById(adapterList)

            adapter.list = sortedList
        })



    }
    //sorts shopping carts by ascending id
    private fun sortIncreasingById(originalArray: ArrayList<ProductDetail>):ArrayList<ProductDetail> {
        val sortingArray = originalArray.sortedWith(compareBy { it.id })
        val sortedArray = ArrayList<ProductDetail>()

        for (product: ProductDetail in sortingArray) {

            sortedArray.add(product)
        }
        return sortedArray
    }
    //Fragmented messages make api request for details of products in shopping carts data
    private fun getProductsDetail()  {
        for (product in chosenCart.products) {
            Log.e("product","productId:${product.productId}")

                 mainActivityViewModel.getProductDetail(product.productId!!)
        }
    }
    //receives data from other fragment
    private fun handleArguments() {
        arguments?.let {
            chosenCart = CartDetailFragmentArgs.fromBundle(it).cart
        }

    }
    //binds the values that should be printed in the fragment
    private fun valueBind() {
        binding.cartDetailDate.text = modifyDateLayout(chosenCart.date!!)[0]
        binding.cartDetailTime.text = modifyDateLayout(chosenCart.date!!)[1]

       //binding.cartDetailTotalPrice.text =totalPrice.toString()
    }
    //Connects to the adapter class
    private fun setupAdapter() {
        binding.cartDetailList.layoutManager = LinearLayoutManager(context)
        adapter = CartDetailAdapter()
        binding.cartDetailList.adapter = adapter
    }
    //finds counts in shopping cart from other fragment
    private fun getProductsQuantity(){
        for(quantity in chosenCart.products){
            quantity.quantity?.let { quantityList.add(it) }
        }
        adapter.quantityList = quantityList
    }
    //handles click events
    private fun handleOnClickEvents() {
        binding.apply {
            backToCartListButton.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
    //Parse the data format to ["Date","Time"]
    @Throws(ParseException::class)
    private fun modifyDateLayout(inputDate: String): List<String> {

        val allDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(inputDate)
        val parsedDate = allDate?.let { SimpleDateFormat("dd.MM.yyyy HH:mm").format(it) }
        return parsedDate!!.split(" ")
    }
}