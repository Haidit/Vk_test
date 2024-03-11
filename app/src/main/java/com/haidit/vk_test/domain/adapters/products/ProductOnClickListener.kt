package com.haidit.vk_test.domain.adapters.products

import com.haidit.vk_test.domain.models.Product

interface ProductOnClickListener {

    fun onClicked(product: Product)
}