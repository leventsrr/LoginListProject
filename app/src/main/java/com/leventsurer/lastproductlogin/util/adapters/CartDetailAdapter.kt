package com.leventsurer.lastproductlogin.util.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.leventsurer.lastproductlogin.databinding.CartDetailCardBinding
import com.leventsurer.lastproductlogin.model.getAllProducts.ProductItem

class CartDetailAdapter():RecyclerView.Adapter<CartDetailAdapter.CartDetailHolder>() {
    private lateinit var context : Context
    class CartDetailHolder(val binding:CartDetailCardBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(){

        }

    }

   var list = ArrayList<ProductItem>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartDetailHolder {
       val binding = CartDetailCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CartDetailHolder(binding)
    }

    override fun onBindViewHolder(holder: CartDetailHolder, position: Int) {
        holder.binding.apply {
            val currentItem = list[position]
                cartProductName.text = currentItem.title
            cartProductPrice.text = currentItem.price.toString()

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}