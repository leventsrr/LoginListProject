package com.leventsurer.lastproductlogin.util.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.leventsurer.lastproductlogin.databinding.CartCardBinding
import com.leventsurer.lastproductlogin.model.getAllCart.Carts
import com.leventsurer.lastproductlogin.model.getAllCart.Products
import java.text.ParseException
import java.text.SimpleDateFormat

class CartAdapter():RecyclerView.Adapter<CartAdapter.CartHolder>() {
    private lateinit var context : Context
    private var date=ArrayList<String>()
    class CartHolder(val binding:CartCardBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(cart:Carts){
        }
    }

    var list =ArrayList<Carts>()
    set(value) {
        field=value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartHolder {
        context = parent.context
        val binding = CartCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CartHolder(binding)
    }

    @Throws(ParseException::class)
    //Parse the data format to ["Date","Time"]
    private fun modifyDateLayout(inputDate: String): List<String> {

        val allDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(inputDate)
        val parsedDate= allDate?.let { SimpleDateFormat("dd.MM.yyyy HH:mm").format(it) }
        return parsedDate!!.split(" ")
    }
    override fun onBindViewHolder(holder: CartHolder, position: Int) {
        holder.binding.apply {
            val currentItem = list[position]

            cartPrice.text = "Price : ${currentItem.totalPrice}"
            cartDate.text = "Date : ${modifyDateLayout(currentItem.date!!)[0]}"
            cartTime.text = "Time : ${modifyDateLayout(currentItem.date!!)[1]}"
        }

        handleItemOnClick(holder,position)
    }

    private fun handleItemOnClick(holder: CartHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onClickListenerCustom?.let {
                it(list[position])
            }
        }
    }

    private var onClickListenerCustom: ((cart: Carts) -> Unit)? = null
    fun setOnClickListenerCustom(f: (cart: Carts) -> Unit) {
        Log.e("TAG", "setOnClickListenerCustom: ")
        onClickListenerCustom = f
    }

    override fun getItemCount(): Int {
        return list.size
    }




}