package com.haidit.vk_test.ui.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.haidit.vk_test.R
import com.haidit.vk_test.databinding.FragmentHomeBinding
import com.haidit.vk_test.domain.adapters.products.ProductOnClickListener
import com.haidit.vk_test.domain.adapters.products.ProductsAdapter
import com.haidit.vk_test.domain.models.Product

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeFragmentViewModel by activityViewModels()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var menuHost: MenuHost
    private lateinit var menuProvider: MenuProvider
    private lateinit var adapter: ProductsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(requireView())
        menuHost = requireActivity()
        setMenuProvider()
        menuHost.addMenuProvider(menuProvider)
        setAdapter()
        makeRequest()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        menuHost.removeMenuProvider(menuProvider)
    }

    private fun setMenuProvider() {
        menuProvider = object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.search_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.actionSearch -> {
                        val searchView: SearchView = menuItem.actionView as SearchView

                        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                            override fun onQueryTextSubmit(p0: String?): Boolean {
                                return false
                            }

                            override fun onQueryTextChange(msg: String): Boolean {
                                filter(msg)
                                return false
                            }
                        })
                        true
                    }

                    else -> false
                }
            }
        }
    }

    private fun setAdapter(){
        adapter = ProductsAdapter(requireContext(), viewModel.productsList, object : ProductOnClickListener{
            override fun onClicked(product: Product) {
                Toast.makeText(context, product.id.toString(), Toast.LENGTH_LONG).show()
            }
        })

        val productRV = binding.productsList
        productRV.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        productRV.adapter = adapter
    }

    private fun makeRequest() {
        if (viewModel.productsList.size != 0) {
            return
        }
        val url = "https://dummyjson.com/products?skip=40&limit=20"
        val queue = Volley.newRequestQueue(requireContext())

        val request = StringRequest(Request.Method.GET, url, { result ->
            viewModel.parseData(result)
            adapter.notifyDataSetChanged()
        }, { _ ->
            Snackbar.make(binding.root, getString(R.string.api_error), Snackbar.LENGTH_LONG).show()
        })
        queue.add(request)
    }

    private fun filter(text: String) {
        val filteredList: ArrayList<Product> = ArrayList()

        for (item in viewModel.productsList) {
            if (item.title.lowercase().contains(text.lowercase()) or item.description.lowercase().contains(text.lowercase())) {
                filteredList.add(item)
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(requireContext(), getString(R.string.no_data_found), Toast.LENGTH_SHORT)
                .show()
        } else {
            adapter.filterList(filteredList)
        }
    }
}