package com.haidit.vk_test.ui.detailed

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.haidit.vk_test.R
import com.haidit.vk_test.databinding.FragmentDetailedBinding

class DetailedFragment : Fragment(R.layout.fragment_detailed) {

    private val viewModel: DetailedFragmentViewModel by activityViewModels()
    private lateinit var binding: FragmentDetailedBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDetailedBinding.bind(requireView())

    }

}