package com.leventsurer.lastproductlogin.util.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.leventsurer.lastproductlogin.databinding.FilterCategoryBinding
import com.leventsurer.lastproductlogin.model.CategoryStatus

class CategoryAdapter() : RecyclerView.Adapter<CategoryAdapter.CategoryHolder>() {

    private lateinit var context: Context

    class CategoryHolder(val binding: FilterCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    private var _list = ArrayList<CategoryStatus>()
    val list get() = _list

    @SuppressLint("NotifyDataSetChanged")
    fun setList(newList: ArrayList<CategoryStatus>) {
        _list.clear()
        _list.addAll(newList)
//        _list.add(newList.size, ImageUrls("", false, -1))

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        context = parent.context
        val binding =
            FilterCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.binding.apply {

            category.text = list[position].categoryName
            changeCategoryColor(position,category)
            categoryClickHandle(clCategory,position)


        }
    }

    private fun categoryClickHandle(clCategory:ConstraintLayout,position:Int) {
        clCategory.setOnClickListener {
            onClickListenerCustom?.let {
                it(list[position], position)
            }
        }
    }

    private fun changeCategoryColor(position:Int,category:TextView) {
        if (list[position].isChoosen) {
            category.setTextColor(Color.parseColor("#FDBF00"))
        } else {
            category.setTextColor(Color.parseColor("#52565C"))
        }
    }

    private var onClickListenerCustom: ((categoryObject: CategoryStatus, position: Int) -> Unit)? =
        null

    fun setOnClickListenerCustom(f: (categoryObject: CategoryStatus, position: Int) -> Unit) {

        onClickListenerCustom = f
    }

    override fun getItemCount(): Int {
        return list.size
    }

}