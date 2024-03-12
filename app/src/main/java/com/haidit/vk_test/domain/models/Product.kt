package com.haidit.vk_test.domain.models

data class Product(
    val id: Int = 0,
    var title: String = "",
    var description: String = "",
    var price: Int = 0,
    var discountPercentage: Double = 0.0,
    var rating: Double = 0.0,
    var stock: Int = 0,
    var brand: String = "",
    var category: String = "",
    var thumbnail: String = "",
    var images: ArrayList<String> = ArrayList()
)