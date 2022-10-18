package com.leventsurer.lastproductlogin.util.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.leventsurer.lastproductlogin.R
import com.leventsurer.lastproductlogin.data.model.ProductFavoriteStatus
import com.leventsurer.lastproductlogin.data.model.getAllProducts.ProductItem
import com.leventsurer.lastproductlogin.databinding.FavoriteProductListCardBinding

class FavoriteProductsAdapter() : RecyclerView.Adapter<FavoriteProductsAdapter.FavoriteProductCartHolder>() {
    private lateinit var context: Context
    fun setFilteredList(filteredList: ArrayList<ProductFavoriteStatus>) {
        this.list = filteredList
        //notifyDataSetChanged()
    }

    class FavoriteProductCartHolder(val binding: FavoriteProductListCardBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(productItem: ProductItem) {
            itemView.findViewById<TextView>(R.id.productPrice).text = productItem.price.toString()
            itemView.findViewById<TextView>(R.id.productTitle).text = productItem.title
        }

    }
    //update adapter products list
    var list = ArrayList<ProductFavoriteStatus>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteProductCartHolder {
        context = parent.context
        val binding = FavoriteProductListCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteProductCartHolder(binding)

    }

    override fun onBindViewHolder(holder: FavoriteProductCartHolder, position: Int) {
        setupViewHolder(holder, position)
        handleItemOnClick(holder, position)
    }

    private fun setupViewHolder(holder: FavoriteProductCartHolder, position: Int) {
        bindVariables(holder, position)
    }
    //Defines the incoming data to the required places
    private fun bindVariables(holder: FavoriteProductCartHolder, position: Int) {
        holder.binding.apply {
            Glide.with(context).load(list[position].image).into(favoriteProductImage)
            Log.e("DENEME", "3")
            favoriteProductName.text = list[position].title
            favoriteProductPrice.text = "${list[position].price} TL"

        }
    }
    //Sending the id number of the clicked product to the detail page
    private fun handleItemOnClick(holder: FavoriteProductCartHolder, position: Int) {

        holder.binding.favoriteProductStatus.setOnClickListener {
            onClickListenerCustom?.let {
                it(list[position])
            }
        }
    }

    private var onClickListenerCustom: ((productFavoriteStatus: ProductFavoriteStatus) -> Unit)? = null
    fun setOnClickListenerCustom(f: ((productFavoriteStatus: ProductFavoriteStatus) -> Unit)) {
        Log.e("TAG", "setOnClickListenerCustom: ")
        onClickListenerCustom = f
    }

    override fun getItemCount(): Int {
        return list.size
    }

}