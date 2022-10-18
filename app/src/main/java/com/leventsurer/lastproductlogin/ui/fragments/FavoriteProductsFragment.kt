package com.leventsurer.lastproductlogin.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.leventsurer.lastproductlogin.data.model.ProductFavoriteStatus
import com.leventsurer.lastproductlogin.databinding.FragmentFavoriteProductsBinding
import com.leventsurer.lastproductlogin.util.adapters.FavoriteProductsAdapter
import com.leventsurer.lastproductlogin.viewModel.FavoriteProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteProductsFragment : Fragment() {
    private var _binding: FragmentFavoriteProductsBinding? = null
    private val binding: FragmentFavoriteProductsBinding get() = _binding!!
    private val favoriteProductViewModel: FavoriteProductViewModel by viewModels()
    private var adapterList = ArrayList<ProductFavoriteStatus>()


    private lateinit var searchView: SearchView

    private lateinit var adapter: FavoriteProductsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        subscribeObserve()
        setupSearch()
    }
    //product deletion message from favorites
    private fun showToastMessage(productName:String){
        Toast.makeText(context,"$productName Deleted From Favorites", Toast.LENGTH_SHORT).show()
    }
    // connects with the adapter class
    private fun setupAdapter() {
        binding.favoriteProductsList.layoutManager = LinearLayoutManager(context)
        adapter = FavoriteProductsAdapter()
        binding.favoriteProductsList.adapter = adapter
        adapter.setOnClickListenerCustom { item ->
            removeFromFavorities(item)
        }

    }
    // delete items from favorites
    private fun removeFromFavorities(productFavoriteStatus: ProductFavoriteStatus){
        favoriteProductViewModel.removeProductFromFavorites(productFavoriteStatus)
        showToastMessage(productFavoriteStatus.title)
    }
    // listen to favorite products
    private fun subscribeObserve() {
        favoriteProductViewModel.favoriteProducts.observe(viewLifecycleOwner) { response ->
            adapterList.clear()
            adapterList.addAll(response)
            adapter.list = adapterList

        }
    }
    // filter products by search
    private fun filterList(newText: String) {
        val filteredList = ArrayList<ProductFavoriteStatus>()
        for (productItem: ProductFavoriteStatus in adapterList) {
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
    // link to the search view
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
}