package com.leventsurer.lastproductlogin.ui.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.leventsurer.lastproductlogin.MainActivity
import com.leventsurer.lastproductlogin.databinding.FragmentProductDetailBinding
import com.leventsurer.lastproductlogin.model.getProductDetail.ProductDetail
import com.leventsurer.lastproductlogin.viewModel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
//Page showing product detail
@AndroidEntryPoint
class ProductDetailFragment : Fragment() {
    private var _binding: FragmentProductDetailBinding? = null
    private val binding: FragmentProductDetailBinding get() = _binding!!
    private var adapterList: ProductDetail? = null
    private val mainActivityViewModel: MainActivityViewModel by viewModels()
    var productId = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            handleArguments()

        }

    }
   // Bringing product details from another page
    private fun handleArguments() {
        arguments?.let {
            productId = ProductDetailFragmentArgs.fromBundle(it).productId
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRequest()
        subscribeObserve()
        backButtonClickHandler()
        (requireActivity() as MainActivity).hideBottomNavigation()
    }

    private fun backButtonClickHandler() {
        binding.backToProductListButton.setOnClickListener{
            //findNavController.navigate(R.id.action.....)
            //findNavController().navigate(R.id.action_productDetailFragment_to_productListFragment3)
            findNavController().popBackStack()

        }
    }


    //Requesting product detail from API according to id
    private fun setupRequest() {
        mainActivityViewModel.getProductDetail(productId)
    }

    //Managing incoming data
    private fun subscribeObserve() {
        mainActivityViewModel.productDetail.observe(viewLifecycleOwner) { response ->
            adapterList = response
            handleResponse()
        }
    }

    @SuppressLint("SetTextI18n")
    //Placing incoming data where necessary in XML
    private fun handleResponse() {

        adapterList?.let {
            context?.let { fragmentContext -> loadProductImage(fragmentContext,it) }
            bindVariable(it)
        } ?: {
            Log.e("Hata Mesajı","Gelen Response Null")
        }

    }

    @SuppressLint("SetTextI18n")
    private fun bindVariable(productDetail: ProductDetail) {
        binding.productDetailTitle.text = productDetail.title
        //binding.productDetailCategory.text = it.category

        binding.productDetailRate.text = productDetail.rating?.rate.toString()
        binding.productDetailPrice.text = "${productDetail.price.toString()} TL"
        binding.productDetailDescription.text = productDetail.description
    }

    //Placing incoming data where necessary in XML
    private fun loadProductImage(fragmentContext: Context,productDetail:ProductDetail){
        Glide.with(fragmentContext).load(productDetail.image).into(binding.productDetailImage)
    }



}