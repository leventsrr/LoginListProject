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

    private fun setupProductListRequest() {
        categoryName?.let {
            mainActivityViewModel.getProducts(it)
        } ?: run {
            mainActivityViewModel.getProducts(null)
        }
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
                Log.e("textChange", "textChange geldi")
                filterList(newText)
                return true
            }

            override fun onFocusChange(p0: View?, p1: Boolean) {
            }
        })
    }

    //aranan kelimeye göre filtrelemenin yapılması
    private fun filterList(newText: String) {
        val filteredList = ArrayList<ProductItem>()
        Log.e("adapterList", adapterList.toString())
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

    //adapter ve ProductListFragment bağlantısı
    private fun setupAdapter() {
        binding.products.layoutManager = GridLayoutManager(context, 2)
        adapter = ProductAdapter()
        binding.products.adapter = adapter
        adapter.setOnClickListenerCustom {
            goToDetailFragment(it)
        }
    }

    fun sortTheProductArray(
        originalArray: ArrayList<ProductItem>,
        isdecreasingPrice: Boolean
    ): ArrayList<ProductItem> {

        if (isdecreasingPrice) {
            val sortingArray = originalArray.sortedByDescending { it.price }
            val sortedArray = ArrayList<ProductItem>()

            for (product: ProductItem in sortingArray) {

                sortedArray.add(product)
            }
            Log.e("Sıralam", sortedArray.toString())
            return sortedArray
        } else {
            val sortingArray = originalArray.sortedWith(compareBy { it.price })
            val sortedArray = ArrayList<ProductItem>()

            for (product: ProductItem in sortingArray) {

                sortedArray.add(product)
            }
            Log.e("Sıralam", sortedArray.toString())
            return sortedArray
        }
    }


    //Tıklanılan ürünün detay sayfasına yönlendirme yapılması
    private fun goToDetailFragment(productId: Int) {
        val action =
            ProductsListFragmentDirections.actionProductListFragment3ToProductDetailFragment(
                productId
            )
        findNavController().navigate(action)

    }


}