package com.erolaksoy.androidkotlinappformybackend.ui.product.productDetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.erolaksoy.androidkotlinappformybackend.R
import com.erolaksoy.androidkotlinappformybackend.consts.ApiConsts
import com.erolaksoy.androidkotlinappformybackend.databinding.ProductDetailFragmentBinding
import com.erolaksoy.androidkotlinappformybackend.services.apiServices.PhotoService
import com.erolaksoy.androidkotlinappformybackend.ui.user.UserActivity

class ProductDetailFragment : Fragment() {

    private lateinit var viewModel: ProductDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val args = ProductDetailFragmentArgs.fromBundle(requireArguments())
        val binding = ProductDetailFragmentBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(ProductDetailViewModel::class.java)
        UserActivity.setErrorState(viewModel, viewLifecycleOwner)
        UserActivity.setLoadingState(viewModel, viewLifecycleOwner)
        viewModel.getProductById(args.productId).observe(viewLifecycleOwner, Observer {
            if (it != null) {
                binding.txtName.text = it.Name.toString()
                binding.txtColor.text = it.Color.toString()
                binding.txtPrice.text = it.Price.toString()
                binding.txtCategoryName.text = it.Category?.Name
                binding.txtStock.text = it.Stock.toString()
                val uri = ApiConsts.photoApiBaseUrl+it.PhotoPath
                Glide.with(this).load(uri).into(binding.productImage)
            }
        })

        return binding.root
    }


}