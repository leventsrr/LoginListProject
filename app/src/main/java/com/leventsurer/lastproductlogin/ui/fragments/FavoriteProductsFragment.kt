package com.leventsurer.lastproductlogin.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.leventsurer.lastproductlogin.R
import com.leventsurer.lastproductlogin.data.model.FavoriteProduct
import com.leventsurer.lastproductlogin.data.model.getAllProducts.ProductItem
import com.leventsurer.lastproductlogin.databinding.FragmentFavoriteProductsBinding
import com.leventsurer.lastproductlogin.databinding.FragmentProductListBinding
import com.leventsurer.lastproductlogin.util.adapters.FavoriteProductsAdapter
import com.leventsurer.lastproductlogin.util.adapters.ProductAdapter
import com.leventsurer.lastproductlogin.viewModel.FavoriteProductViewModel
import com.leventsurer.lastproductlogin.viewModel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteProductsFragment : Fragment() {
    private var _binding: FragmentFavoriteProductsBinding? = null
    private val binding: FragmentFavoriteProductsBinding get() = _binding!!
    private val favoriteProductViewModel: FavoriteProductViewModel by viewModels()
    private var adapterList = ArrayList<FavoriteProduct>()


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
    }


    private fun setupAdapter() {
        binding.favoriteProductsList.layoutManager = LinearLayoutManager(context)
        adapter = FavoriteProductsAdapter()
        binding.favoriteProductsList.adapter = adapter

    }

    private fun subscribeObserve() {
        favoriteProductViewModel.favoriteProducts.observe(viewLifecycleOwner) { response ->
            adapterList.clear()
            adapterList.addAll(response)
            adapter.list = adapterList

        }
    }
}