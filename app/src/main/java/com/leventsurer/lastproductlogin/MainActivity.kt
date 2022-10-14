package com.leventsurer.lastproductlogin

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.leventsurer.lastproductlogin.databinding.ActivityMainBinding
import com.leventsurer.lastproductlogin.data.database.SharedPreferences
import com.leventsurer.lastproductlogin.viewModel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var prefManager: SharedPreferences
    private lateinit var context: Context
    private val mainActivityViewModel: MainActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    //making the necessary definitions at the opening of the fragment
    private fun init(){
        prefManager = SharedPreferences(this)
        setupBottomNavigationView()


    }
    //Do not show login screen if there is login information defined in shared preferences
    private fun checkLogin(){
        if(!prefManager.isLogin()){
            //ProductList e yönlendir
            //setupBottomNavigationView()
        }else{

            navController.navigate(R.id.productListFragment3)
        }
    }
    //hide bottomNavigation
    fun hideBottomNavigation() {
        binding.bottomNavigationView.visibility = View.GONE
    }
    //show bottomNavigation
    fun showBottomNavigation() {
        binding.bottomNavigationView.visibility = View.VISIBLE
    }
    //bottom navigation bar min routing operations
    private fun setupBottomNavigationView() {

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)

        checkLogin()


    }
}