package com.haidit.vk_test.ui.home

import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.haidit.vk_test.domain.models.Product
import org.json.JSONArray
import org.json.JSONObject

class HomeFragmentViewModel : ViewModel() {

    val productsList = ArrayList<Product>()

    fun parseData(result: String) {
        val mainObject = JSONObject(result)
        val mainObjectsList = mainObject.getJSONArray("products")


        for (i in 0 until mainObjectsList.length()) {
            val itemData = mainObjectsList[i] as JSONObject
            val item = Product(
                itemData.getInt("id"),
                itemData.getString("title"),
                itemData.getString("description"),
                itemData.getString("thumbnail")
            )
            productsList.add(item)
        }
    }
}