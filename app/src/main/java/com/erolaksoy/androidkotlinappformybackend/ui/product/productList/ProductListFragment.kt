package com.erolaksoy.androidkotlinappformybackend.ui.product.productList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.erolaksoy.androidkotlinappformybackend.databinding.ProductListFragmentBinding
import com.erolaksoy.androidkotlinappformybackend.ui.adapters.ProductListAdapter
import com.erolaksoy.androidkotlinappformybackend.ui.user.UserActivity

class ProductListFragment : Fragment() {

    private var productListAdapter: ProductListAdapter? = null
    private var page: Int = 0
    private var isLoading = false
    private var isLastPage = false
    private lateinit var layoutManager: LinearLayoutManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = ProductListFragmentBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProvider(this).get(ProductListViewModel::class.java)

        UserActivity.setLoadingState(viewModel, viewLifecycleOwner)
        UserActivity.setErrorState(viewModel, viewLifecycleOwner)

        binding.productAddFAB.setOnClickListener {
            val action =
                ProductListFragmentDirections.actionProductListFragmentToProductAddFragment()
            findNavController().navigate(action)
        }

        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.productListRecyclerView.layoutManager = layoutManager

        //Paging
        if (page == 0) viewModel.getProducts(page)
        else binding.productListRecyclerView.adapter = productListAdapter

        viewModel.products.observe(viewLifecycleOwner, Observer { productList ->
            if ((productList.size == 0 || productList.size==null) && page != 0) {   //size 0 ve page 0'dan farklı olursa son sayfada oluruz
                productListAdapter?.removeLoading()
                isLoading = false
                isLastPage = true
            } else {
                if (page == 0) {    //ilk kez veri çekiyorsak
                    binding.productListRecyclerView.apply {
                        productListAdapter = ProductListAdapter(productList, onClickListener = {
                            val action = ProductListFragmentDirections.actionProductListFragmentToProductDetailFragment(it.Id)
                            findNavController().navigate(action)
                        })
                        adapter = productListAdapter
                    }
                }
                if (page != 0) {
                    productListAdapter?.removeLoading()
                    isLoading = false
                    val isExist = productListAdapter!!.productList.contains(productList[0])
                    if (!isExist) productListAdapter!!.addProduct(productList)
                }
            }
        })

        binding.productListRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!isLoading && !isLastPage) {
                    Log.i(
                        "OkHttp",
                        "$visibleItemCount + $firstVisibleItemPosition >= $totalItemCount"
                    )
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
                        isLoading = true
                        productListAdapter?.addLoading()
                        page += 5
                        viewModel.getProducts(page)
                    }
                }
            }
        })

        return binding.root
    }


}