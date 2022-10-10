package com.leventsurer.lastproductlogin.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.leventsurer.lastproductlogin.R
import com.leventsurer.lastproductlogin.databinding.FragmentProfileBinding
import com.leventsurer.lastproductlogin.di.SharedPreferences

class ProfileFragment : Fragment() {

    private lateinit var binding:FragmentProfileBinding
    private lateinit var prefManager:SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeSharedPreferences()
        handleClickEvent()
    }
    //define shared preferences
    private fun initializeSharedPreferences() {
        prefManager = SharedPreferences(requireContext())
    }
    //manage click events
    private fun handleClickEvent() {
        binding.apply {
            logoutButton.setOnClickListener {
                logOut()

            }
            binding.userCartCardButton.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_cartFragment)
            }
        }
    }
    //Logout Account
    private fun logOut(){
        prefManager.removeData()
        returnLoginScreen()


    }

    //return to login screen
    private fun returnLoginScreen() {
        val action = ProfileFragmentDirections.actionProfileFragmentToLoginFragment()
        findNavController().navigate(action)
    }

}