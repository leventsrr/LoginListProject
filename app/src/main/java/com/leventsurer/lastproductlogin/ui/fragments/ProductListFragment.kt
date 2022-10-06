package com.leventsurer.lastproductlogin.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.leventsurer.lastproductlogin.MainActivity
import com.leventsurer.lastproductlogin.R
import com.leventsurer.lastproductlogin.databinding.FragmentProductListBinding
import com.leventsurer.lastproductlogin.model.getAllProducts.ProductItem
import com.leventsurer.lastproductlogin.util.adapters.ProductAdapter
import com.leventsurer.lastproductlogin.util.constants.Cons.CATEGORY_NAME_BUNDLE_KEY
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

    private fun subscribeObserve() {
        mainActivityViewModel.product.observe(viewLifecycleOwner) { response ->
            adapterList.clear()
            adapterList.addAll(response)
            adapter.list = adapterList

        }
    }

    private fun handleClickEvents() {
        binding.filter.setOnClickListener {
            findNavController().navigate(R.id.action_productListFragment3_to_filterFragment)
        }
        binding.sortButton.setOnClickListener {
            val dialog = BottomSheetDialog(requireContext())
            val view = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null)
            dialog.setCancelable(false)
            dialog.setContentView(view)
            dialog.show()
            val sortIncButton = view.findViewById<Button>(R.id.sortByIncPriceButton)
            val sortDecButton = view.findViewById<Button>(R.id.sortByDecsPriceButton)

            //Ürünleri artan fiyata göre sırala
            fun sortByIncreasing() {

                val sortedList: ArrayList<ProductItem> = sortTheProductArray(adapterList, false)

                adapter.list = sortedList
                dialog.dismiss()
            }
            sortIncButton.setOnClickListener {
                sortByIncreasing()

            }

            //Ürünleri azalan fiyata göre sırala
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
            mainActivityViewModel.getProducts(it)
        } ?: run {
            mainActivityViewModel.getProducts(null)
        }
    }
    private fun setupBottomSheet(){

    }
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
        adapter.setOnClickListenerCustom {
            goToDetailFragment(it)
        }
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

    private fun sortIncreasing(originalArray: ArrayList<ProductItem>):ArrayList<ProductItem> {
        val sortingArray = originalArray.sortedWith(compareBy { it.price })
        val sortedArray = ArrayList<ProductItem>()

        for (product: ProductItem in sortingArray) {

            sortedArray.add(product)
        }
        Log.e("Sıralam", sortedArray.toString())
        return sortedArray
    }

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