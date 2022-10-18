package com.leventsurer.lastproductlogin.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.leventsurer.lastproductlogin.MainActivity
import com.leventsurer.lastproductlogin.R
import com.leventsurer.lastproductlogin.data.model.ProductFavoriteStatus
import com.leventsurer.lastproductlogin.databinding.FragmentProductListBinding
import com.leventsurer.lastproductlogin.data.model.getAllProducts.ProductItem
import com.leventsurer.lastproductlogin.util.adapters.ProductAdapter
import com.leventsurer.lastproductlogin.util.constants.Cons.CATEGORY_NAME_BUNDLE_KEY
import com.leventsurer.lastproductlogin.viewModel.FavoriteProductViewModel
import com.leventsurer.lastproductlogin.viewModel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
//screen where products are listed and operations such as sorting are done
@AndroidEntryPoint
class ProductsListFragment : Fragment() {
    private var _binding: FragmentProductListBinding? = null
    private val binding: FragmentProductListBinding get() = _binding!!
    private val mainActivityViewModel: MainActivityViewModel by viewModels()
    private var adapterList = ArrayList<ProductItem>()
    private var categoryName: String? = null
    private val favoriteProductViewModel: FavoriteProductViewModel by viewModels()

    private lateinit var searchView: SearchView

    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            categoryName = it.getString(CATEGORY_NAME_BUNDLE_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        handleClickEvents()
        setupAdapter()
        setupSearch()
        subscribeObserve()

    }
    //transmits  incoming data to adapter
    private fun subscribeObserve() {
        mainActivityViewModel.product.observe(viewLifecycleOwner) { response ->
            adapterList.clear()
            adapterList.addAll(response)
            adapter.list = adapterList

        }
    }
    //handles click events
    private fun handleClickEvents() {
        filterButtonClickHandler()
        sortButtonClickHandler()
        resetFilter()
    }



    private fun resetFilter() {

        binding.closeFilterButton.setOnClickListener{
            mainActivityViewModel.getProducts(null)
            findNavController().currentBackStackEntry?.savedStateHandle?.set(
                CATEGORY_NAME_BUNDLE_KEY,
                null
            )

        }
    }

    //Allows sorting of products
    private fun sortButtonClickHandler() {
        binding.sortButton.setOnClickListener {
            val dialog = BottomSheetDialog(requireContext())
            val view = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null)
            dialog.setCancelable(false)
            dialog.setContentView(view)
            dialog.show()
            val sortIncButton = view.findViewById<Button>(R.id.sortByIncPriceButton)
            val sortDecButton = view.findViewById<Button>(R.id.sortByDecsPriceButton)

            //Sort products by increasing price
            fun sortByIncreasing() {

                val sortedList: ArrayList<ProductItem> = sortTheProductArray(adapterList, false)

                adapter.list = sortedList
                dialog.dismiss()
            }
            sortIncButton.setOnClickListener {
                sortByIncreasing()

            }

            //Sort products by decreasing price
            fun sortByDecreasing() {

                val sortedList: ArrayList<ProductItem> = sortTheProductArray(adapterList, true)

                adapter.list = sortedList
                dialog.dismiss()
            }
            sortDecButton.setOnClickListener {
                sortByDecreasing()

            }

        }
    }
    //filter button event management
    private fun filterButtonClickHandler(){
        binding.filter.setOnClickListener {
            findNavController().navigate(R.id.action_productListFragment3_to_filterFragment)
        }
    }
    //screen where products are listed and operations such as sorting are done
    private fun initialize() {
        (requireActivity() as MainActivity).showBottomNavigation()
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(
            CATEGORY_NAME_BUNDLE_KEY
        )?.observe(viewLifecycleOwner) { result ->
                categoryName = result
                setupProductListRequest()
            }
        setupProductListRequest()

    }
    //getting products from api
    private fun setupProductListRequest() {
        categoryName?.let {
            Log.e("categoryName","$categoryName")
            mainActivityViewModel.getProducts(it)
        } ?: run {
            Log.e("categoryName","$categoryName")
            mainActivityViewModel.getProducts(null)
        }
    }
    //product search
    private fun setupSearch() {
        searchView = binding.searchView
        searchView.clearFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            View.OnFocusChangeListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterList(newText)
                return true
            }

            override fun onFocusChange(p0: View?, p1: Boolean) {
            }
        })
    }
    //filtering products by search word
    private fun filterList(newText: String) {
        val filteredList = ArrayList<ProductItem>()
        for (productItem: ProductItem in adapterList) {
            if (productItem.title!!.lowercase().contains(newText.lowercase())) {
                filteredList.add(productItem)
            }
        }
        if (filteredList.isEmpty()) {
            Log.e("arama", "data yok")
        } else {
            adapter.setFilteredList(filteredList)
        }
    }
    //Connecting adapter and ProductListFragment
    private fun setupAdapter() {
        binding.products.layoutManager = GridLayoutManager(context, 2)
        adapter = ProductAdapter()
        binding.products.adapter = adapter
        adapter.goToDetailPage {
            goToDetailFragment(it)
        }
        //add product to favorities
        adapter.addToFavoritePage { product->
            addProductToFavorities(product)
            showToastMessage(product.title)

        }
    }
    private fun showToastMessage(productName:String){
        Toast.makeText(context,"$productName Added To Favorites",Toast.LENGTH_SHORT).show()
    }
    //add product to favorities
    private fun addProductToFavorities(product:ProductFavoriteStatus) {


        favoriteProductViewModel.addProductToFavorities(ProductFavoriteStatus(product.id,product.title,product.price,product.description,product.category,
            product.image,product.rating,true))
    }
    //sort products by price
    private fun sortTheProductArray(
        originalArray: ArrayList<ProductItem>,
        isdecreasingPrice: Boolean
    ): ArrayList<ProductItem> {

        return if (isdecreasingPrice) {
            sortDescending(originalArray)
        } 
        else {
            sortIncreasing(originalArray)
        }
    }
    //sort products by increasing price
    private fun sortIncreasing(originalArray: ArrayList<ProductItem>):ArrayList<ProductItem> {
        val sortingArray = originalArray.sortedWith(compareBy { it.price })
        val sortedArray = ArrayList<ProductItem>()

        for (product: ProductItem in sortingArray) {

            sortedArray.add(product)
        }
        return sortedArray
    }
    //sort products by decreasing price
    private fun sortDescending(originalArray: ArrayList<ProductItem>): ArrayList<ProductItem> {
        val sortingArray = originalArray.sortedByDescending { it.price }
        val sortedArray=ArrayList<ProductItem>()
        for (product: ProductItem in sortingArray) {
            sortedArray.add(product)
        }
        return sortedArray
    }
    //Redirecting to the detail page of the clicked product
    private fun goToDetailFragment(productId: Int) {
        val action =
            ProductsListFragmentDirections.actionProductListFragment3ToProductDetailFragment(
                productId
            )
        findNavController().navigate(action)

    }


}