package com.leventsurer.lastproductlogin.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.leventsurer.lastproductlogin.databinding.FragmentCartDetailBinding
import com.leventsurer.lastproductlogin.model.getAllCart.Carts

class CartDetailFragment : Fragment() {
    private var _binding : FragmentCartDetailBinding? = null
    private val binding : FragmentCartDetailBinding get() = _binding!!
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleOnClickEvents()

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