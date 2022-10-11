package com.leventsurer.lastproductlogin.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.leventsurer.lastproductlogin.databinding.FragmentCartDetailBinding
import com.leventsurer.lastproductlogin.model.getAllCart.Carts
import com.leventsurer.lastproductlogin.model.getAllProducts.ProductItem
import com.leventsurer.lastproductlogin.model.getProductDetail.ProductDetail
import com.leventsurer.lastproductlogin.util.adapters.CartDetailAdapter
import com.leventsurer.lastproductlogin.util.adapters.ProductAdapter
import com.leventsurer.lastproductlogin.viewModel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.ParseException
import java.text.SimpleDateFormat
@AndroidEntryPoint
class CartDetailFragment : Fragment() {
    private var _binding : FragmentCartDetailBinding? = null
    private val binding : FragmentCartDetailBinding get() = _binding!!

    private var adapterList = ArrayList<ProductDetail>()
    private var filteredAdapterList = ArrayList<ProductDetail>()
    private lateinit var adapter : CartDetailAdapter

    private val mainActivityViewModel:MainActivityViewModel by viewModels()


    private lateinit var chosenCart:Carts
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleArguments()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartDetailBinding.inflate(inflater,container,false)
        return binding.root
    }
    @Throws(ParseException::class)
    //Parse the data format to ["Date","Time"]
    private fun modifyDateLayout(inputDate: String): List<String> {

        val allDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(inputDate)
        val parsedDate= allDate?.let { SimpleDateFormat("dd.MM.yyyy HH:mm").format(it) }
        return parsedDate!!.split(" ")
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleOnClickEvents()

        valueBind()
        setupAdapter()
        getProductsDetail()

    }
    private fun subscribeObserve(){
        mainActivityViewModel.productDetail.observe(viewLifecycleOwner){
            adapterList.addAll(listOf(it))
            filterAdapterList()
            Log.e("adapter", "subscribeObserve: $filteredAdapterList", )
        }
    }

    private fun filterAdapterList() {
        for(product in adapterList){
            if(product !in filteredAdapterList){
                filteredAdapterList.add(product)
            }
        }
        adapter.list = filteredAdapterList
    }

    private fun getProductsDetail() {
        for (product in chosenCart.products){
            Log.e("ID", "getProductsDetail: ${product.productId}", )
            subscribeObserve()
            mainActivityViewModel.getProductDetail(product.productId!!)

        }

    }

    private fun valueBind() {
        binding.cartDetailDate.text = modifyDateLayout(chosenCart.date!!)[0]
        binding.cartDetailTime.text = modifyDateLayout(chosenCart.date!!)[1]
    }

    private fun setupAdapter() {

        binding.cartDetailList.layoutManager  = LinearLayoutManager(context)
        adapter = CartDetailAdapter()
        binding.cartDetailList.adapter = adapter
    }

    private fun handleOnClickEvents() {
        binding.apply {
            backToCartListButton.setOnClickListener {
                val action = CartDetailFragmentDirections.actionCartDetailFragmentToCartFragment()
                findNavController().navigate(action)
            }
        }
    }

    private fun handleArguments(){
        arguments?.let {
            chosenCart = CartDetailFragmentArgs.fromBundle(it).cart
        }
    }


}