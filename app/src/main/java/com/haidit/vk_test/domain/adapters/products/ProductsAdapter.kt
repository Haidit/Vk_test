package com.haidit.vk_test.domain.adapters.products

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.haidit.vk_test.domain.models.Product

class ProductsAdapter(
    private val context: Context,
    var productsList: ArrayList<Product>,
    private val onClickListener: ProductOnClickListener
) : RecyclerView.Adapter<ProductsVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsVH {
        return ProductsVH(context, parent, onClickListener)
    }

    override fun onBindViewHolder(holder: ProductsVH, position: Int) {
        holder.bind(productsList[position])
    }

    override fun getItemCount(): Int = productsList.size

    fun filterList(filterList: ArrayList<Product>) {
        productsList = filterList
        notifyDataSetChanged()
    }
}