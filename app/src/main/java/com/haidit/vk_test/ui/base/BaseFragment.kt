package com.haidit.vk_test.ui.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.haidit.vk_test.R

abstract class BaseFragment(@LayoutRes layoutRes: Int) : Fragment(layoutRes) {

    abstract val viewModel: BaseViewModel
    abstract val binding: ViewBinding
    fun makeRequest(url: String) {
        val queue = Volley.newRequestQueue(requireContext())

        val request = StringRequest(Request.Method.GET, url, { result ->
            onRequestSuccess(result)
        }, { _ ->
            Snackbar.make(
                binding.root,
                getString(R.string.no_connection_message),
                Snackbar.LENGTH_LONG
            ).show()
        })
        queue.add(request)
    }

    open fun onRequestSuccess(result: String){
        viewModel.clearData()
        viewModel.parseData(result)
    }

}