package com.leventsurer.lastproductlogin.util.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.leventsurer.lastproductlogin.databinding.CartDetailCardBinding
import com.leventsurer.lastproductlogin.model.getAllProducts.ProductItem
import com.leventsurer.lastproductlogin.model.getProductDetail.ProductDetail

class CartDetailAdapter():RecyclerView.Adapter<CartDetailAdapter.CartDetailHolder>() {
    private lateinit var context : Context
    class CartDetailHolder(val binding:CartDetailCardBinding):RecyclerView.ViewHolder(binding.root) {


    }

   var list = ArrayList<ProductDetail>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }
    var quantityList = ArrayList<Int>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartDetailHolder {
        context = parent.context
        val binding = CartDetailCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CartDetailHolder(binding)
    }

    override fun onBindViewHolder(holder: CartDetailHolder, position: Int) {
        holder.binding.apply {


            Glide.with(context).load(list[position].image).into(cartProductImage)
                cartProductName.text = list[position].title
                cartProductPrice.text = "${list[position].price} TL"
                cartProductQuantity.text = quantityList[position].toString()
                //totalProductPrice.text = totalPriceList[position].toString()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}