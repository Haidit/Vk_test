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
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.haidit.vk_test.R
import com.haidit.vk_test.databinding.FragmentHomeBinding
import com.haidit.vk_test.domain.adapters.products.ProductOnClickListener
import com.haidit.vk_test.domain.adapters.products.ProductsAdapter
import com.haidit.vk_test.domain.models.Product
import com.haidit.vk_test.ui.base.BaseFragment

class HomeFragment : BaseFragment(R.layout.fragment_home) {

    override val viewModel: HomeFragmentViewModel by activityViewModels()
    override lateinit var binding: FragmentHomeBinding
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

        if (viewModel.productsList.size == 0) {
            makeRequest(makeUrl(0))
        }
        binding.next.setOnClickListener {
            viewModel.page++
            if (viewModel.page > viewModel.lastPage) viewModel.page = viewModel.lastPage
            updateNavigation()
            makeRequest(makeUrl((viewModel.page - 1) * 20))
        }
        binding.prev.setOnClickListener {
            viewModel.page--
            if (viewModel.page <= 0) viewModel.page = 1
            updateNavigation()
            makeRequest(makeUrl((viewModel.page - 1) * 20))
        }
        updateNavigation()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        menuHost.removeMenuProvider(menuProvider)
    }

    override fun onRequestSuccess(result: String) {
        super.onRequestSuccess(result)
        adapter.productsList = viewModel.productsList
        adapter.notifyDataSetChanged()
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
                                viewModel.query = p0!!
                                viewModel.page = 1
                                makeRequest(makeUrl(0))
                                updateNavigation()
                                return false
                            }

                            override fun onQueryTextChange(msg: String): Boolean {
                                if (msg == "" && viewModel.query != "") {
                                    viewModel.query = ""
                                    viewModel.page = 1
                                    binding.thisPage.text = "1"
                                    makeRequest(makeUrl(0))
                                }
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

    private fun setAdapter() {
        adapter = ProductsAdapter(requireContext(),
            viewModel.productsList,
            object : ProductOnClickListener {
                override fun onClicked(product: Product) {
                    val action =
                        HomeFragmentDirections.actionMainFragmentToDetailedFragment(product.id)
                    findNavController().navigate(action)
                }
            })

        val productRV = binding.productsList
        productRV.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        productRV.adapter = adapter
    }

    private fun makeUrl(skip: Int): String {
        val url = if (viewModel.query == "") {
            "https://dummyjson.com/products?skip=$skip&limit=20"
        } else {
            "https://dummyjson.com/products/search?q=${viewModel.query}&skip=$skip&limit=20"
        }
        return url
    }

    private fun updateNavigation() {
        with(binding) {
            thisPage.text = viewModel.page.toString()
            prev.isClickable = viewModel.page != 1
            next.isClickable = viewModel.page != viewModel.lastPage
        }
    }

}