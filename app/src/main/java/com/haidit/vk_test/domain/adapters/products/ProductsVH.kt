package com.haidit.vk_test.domain.adapters.products

import android.content.Context
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.haidit.vk_test.R
import com.haidit.vk_test.databinding.ProductPodBinding
import com.haidit.vk_test.domain.adapters.BaseVH
import com.haidit.vk_test.domain.models.Product

class ProductsVH(
    private val context: Context,
    parent: ViewGroup,
    private val onClickListener: ProductOnClickListener
) : BaseVH<Product>(parent, R.layout.product_pod) {

    private lateinit var binding: ProductPodBinding

    override fun bind(item: Product) {
        binding = ProductPodBinding.bind(itemView)
        with(binding) {
            title.text = item.title
            description.text = item.description
            Glide.with(context).load(item.thumbnail).into(binding.image)
            itemView.setOnClickListener { onClickListener.onClicked(item) }
        }
    }

}