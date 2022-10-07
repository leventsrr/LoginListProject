package com.leventsurer.lastproductlogin.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.leventsurer.lastproductlogin.MainActivity
import com.leventsurer.lastproductlogin.R
import com.leventsurer.lastproductlogin.databinding.FragmentLoginBinding
import com.leventsurer.lastproductlogin.di.SharedPreferences
import dagger.hilt.android.AndroidEntryPoint
//login screen
@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var prefManager: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginButtonClickHandler()


        (requireActivity() as MainActivity).hideBottomNavigation()



    }
    //Actions performed when the login button is pressed
    private fun loginButtonClickHandler() {
        binding.loginButton.setOnClickListener{
            initializePreManager()
            navigateController()

        }
    }

    private fun navigateController() {
        findNavController().navigate(R.id.action_loginFragment_to_productListFragment3)
    }

    private fun initializePreManager() {
        prefManager = SharedPreferences(requireContext())
        prefManager.createSharedPref(true)
    }


}