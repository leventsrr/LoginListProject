package com.leventsurer.lastproductlogin.util.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.leventsurer.lastproductlogin.R
import com.leventsurer.lastproductlogin.databinding.ProductCardBinding
import com.leventsurer.lastproductlogin.model.getAllProducts.ProductItem

class ProductAdapter() : RecyclerView.Adapter<ProductAdapter.ProductHolder>() {
    private lateinit var context: Context
    fun setFilteredList(filteredList:ArrayList<ProductItem>){
        this.list = filteredList
        //notifyDataSetChanged()
    }

    class ProductHolder(val binding: ProductCardBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(productItem: ProductItem) {
            itemView.findViewById<TextView>(R.id.productPrice).text = productItem.price.toString()
            itemView.findViewById<TextView>(R.id.productTitle).text = productItem.title
        }

    }

    var list = ArrayList<ProductItem>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        context = parent.context
        Log.e("DENEME", "5")
        val binding = ProductCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductHolder(binding)

    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        holder.binding.apply {
            Glide.with(context).load(list[position].image).into(productImage)
            Log.e("DENEME", "3")
            productTitle.text = list[position].title
            productPrice.text = "${list[position].price.toString()} TL"
        }

        //Tıklanan ürünün id numarasının detay sayfasına gönderilmesi
        holder.itemView.setOnClickListener{
            onClickListenerCustom?.let {
               if(list[position].id != null){
                   it(list[position].id!!)
               }else{
                   it(-1)
               }
            }
        }
    }

    private var onClickListenerCustom: ((productId: Int) -> Unit)? = null
    fun setOnClickListenerCustom(f: ((productId: Int) -> Unit)) {
        Log.e("TAG", "setOnClickListenerCustom: ")
        onClickListenerCustom = f
    }

    override fun getItemCount(): Int {
        return list.size
    }

}