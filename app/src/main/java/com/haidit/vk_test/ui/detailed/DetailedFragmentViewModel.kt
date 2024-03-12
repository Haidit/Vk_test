package com.haidit.vk_test.ui.detailed

import com.denzcoskun.imageslider.models.SlideModel
import com.haidit.vk_test.domain.models.Product
import com.haidit.vk_test.ui.base.BaseViewModel
import org.json.JSONObject

class DetailedFragmentViewModel : BaseViewModel() {

    var id = 0
    var product = Product()
    val imageList = ArrayList<SlideModel>()

    override fun parseData(result: String) {
        val mainObject = JSONObject(result)
        product = getProduct(mainObject)
    }

    override fun clearData() {
        imageList.clear()
    }

    fun getImages() {
        clearData()
        for (i in 0 until product.images.size) {
            imageList.add(SlideModel(product.images[i]))
        }
    }
}