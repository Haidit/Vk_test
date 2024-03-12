package com.haidit.vk_test.ui.base

import androidx.lifecycle.ViewModel
import com.haidit.vk_test.domain.models.Product
import org.json.JSONObject

abstract class BaseViewModel : ViewModel() {

    abstract fun parseData(result: String)
    fun getProduct(itemData: JSONObject): Product {
        val images = ArrayList<String>()
        val imagesJSONList = itemData.getJSONArray("images")
        for (i in 0 until imagesJSONList.length()) {
            val image = imagesJSONList[i].toString()
            images.add(image)
        }
        return Product(
            itemData.getInt("id"),
            itemData.getString("title"),
            itemData.getString("description"),
            itemData.getInt("price"),
            itemData.getDouble("discountPercentage"),
            itemData.getDouble("rating"),
            itemData.getInt("stock"),
            itemData.getString("brand"),
            itemData.getString("category"),
            itemData.getString("thumbnail"),
            images
        )
    }

    abstract fun clearData()

}