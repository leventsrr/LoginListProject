package com.leventsurer.lastproductlogin.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.leventsurer.lastproductlogin.MainActivity
import com.leventsurer.lastproductlogin.databinding.FragmentFilterBinding
import com.leventsurer.lastproductlogin.model.CategoryStatus
import com.leventsurer.lastproductlogin.util.adapters.CategoryAdapter
import com.leventsurer.lastproductlogin.util.constants.Cons.CATEGORY_NAME_BUNDLE_KEY
import com.leventsurer.lastproductlogin.viewModel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FilterFragment : Fragment() {
    private var _binding: FragmentFilterBinding? = null
    private val binding: FragmentFilterBinding get() = _binding!!
    private val mainActivityViewModel: MainActivityViewModel by viewModels()
    private var categories = ArrayList<CategoryStatus>()

    //private var adapterList = ArrayList<String>()
    private var chosenCategory: String? = null

    private lateinit var adapter: CategoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.chooseFilter.setOnClickListener {
            findNavController().previousBackStackEntry?.savedStateHandle?.set(
                CATEGORY_NAME_BUNDLE_KEY,
                chosenCategory
            )
            findNavController().popBackStack()
        }

        mainActivityViewModel.getCategories()
        setupAdapter()

        mainActivityViewModel.categories.observe(viewLifecycleOwner) { response ->

            for (category in response) {
                categories.add(CategoryStatus(categoryName = category, isChoosen = false))
            }
            adapter.setList(categories)
        }
        (requireActivity() as MainActivity).hideBottomNavigation()

    }

    private fun setupAdapter() {

        binding.categoriesList.layoutManager = LinearLayoutManager(context)
        adapter = CategoryAdapter()
        binding.categoriesList.adapter = adapter
        adapter.setOnClickListenerCustom { item, position ->
            choseCategoryToFilter(item, position)

        }


    }

    //kategori seçimini yöneten fonksiyon
    private fun choseCategoryToFilter(categoryStatus: CategoryStatus, position: Int) {
        for (i in 0 until categories.size) {
            categories[i].isChoosen = i == position
            if (categories[i].isChoosen) {
                chosenCategory = categories[i].categoryName
            }
        }
        adapter.setList(categories)
    }


}