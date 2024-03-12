package com.haidit.vk_test.ui.detailed

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.haidit.vk_test.R
import com.haidit.vk_test.databinding.FragmentDetailedBinding
import com.haidit.vk_test.ui.base.BaseFragment

class DetailedFragment : BaseFragment(R.layout.fragment_detailed) {

    override val viewModel: DetailedFragmentViewModel by activityViewModels()
    override lateinit var binding: FragmentDetailedBinding
    private lateinit var imageSlider: ImageSlider
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDetailedBinding.bind(requireView())
        imageSlider = binding.imageSlider

        val args: DetailedFragmentArgs by navArgs()
        viewModel.id = args.id
        makeRequest("https://dummyjson.com/products/${args.id}")
        binding.basketBtn.setOnClickListener{
            Toast.makeText(context,
                getString(R.string.basket_is_unavailable_message), Toast.LENGTH_LONG).show()
        }
        binding.buyBtn.setOnClickListener {
            Toast.makeText(context,
                getString(R.string.purchases_aren_t_available_message), Toast.LENGTH_LONG).show()
        }
    }


    override fun onRequestSuccess(result: String) {
        super.onRequestSuccess(result)
        viewModel.getImages()
        setViews()
    }
    private fun setViews(){
        imageSlider.setImageList(viewModel.imageList, ScaleTypes.CENTER_INSIDE)
        with (binding){
            title.text = viewModel.product.title
            rating.text = viewModel.product.rating.toString()
            desc.text = viewModel.product.description
            price.text = resources.getString(R.string.price, viewModel.product.price)
            discount.text = resources.getString(R.string.discount, viewModel.product.discountPercentage)
            productId.text = viewModel.product.id.toString()
            brand.text = viewModel.product.brand
            category.text = viewModel.product.category
        }
    }

}