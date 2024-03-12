package com.haidit.vk_test.ui.home

import com.haidit.vk_test.domain.models.Product
import com.haidit.vk_test.ui.base.BaseViewModel
import org.json.JSONObject
import kotlin.math.ceil

class HomeFragmentViewModel : BaseViewModel() {

    val productsList = ArrayList<Product>()

    var page = 1
    var lastPage = 0
    var query = ""

    override fun parseData(result: String) {
        val mainObject = JSONObject(result)
        val mainObjectsList = mainObject.getJSONArray("products")

        lastPage = ceil(mainObject.getInt("total") / 20.0).toInt()
        for (i in 0 until mainObjectsList.length()) {
            val itemData = mainObjectsList[i] as JSONObject
            val item = getProduct(itemData)
            productsList.add(item)
        }
    }

    override fun clearData() {
        productsList.clear()
    }
}