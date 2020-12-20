package com.erolaksoy.androidkotlinappformybackend.ui.product.productList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.erolaksoy.androidkotlinappformybackend.databinding.ProductListFragmentBinding

class ProductListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = ProductListFragmentBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProvider(this).get(ProductListViewModel::class.java)

        binding.productAddFAB.setOnClickListener {
            val action =
                ProductListFragmentDirections.actionProductListFragmentToProductAddFragment()
            findNavController().navigate(action)
        }
        return binding.root
    }


}