package com.erolaksoy.androidkotlinappformybackend.ui.product.productDetail

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.erolaksoy.androidkotlinappformybackend.R
import com.erolaksoy.androidkotlinappformybackend.consts.ApiConsts
import com.erolaksoy.androidkotlinappformybackend.databinding.ProductDetailFragmentBinding
import com.erolaksoy.androidkotlinappformybackend.models.Product
import com.erolaksoy.androidkotlinappformybackend.services.apiServices.PhotoService
import com.erolaksoy.androidkotlinappformybackend.ui.user.UserActivity
import com.google.android.material.snackbar.Snackbar

class ProductDetailFragment : Fragment() {

    private lateinit var viewModel: ProductDetailViewModel
    private lateinit var productNavigation : Product
    @SuppressLint("ResourceType")
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
                productNavigation=it
                binding.txtName.text = it.Name.toString()
                binding.txtColor.text = it.Color.toString()
                binding.txtPrice.text = it.Price.toString()
                binding.txtCategoryName.text = it.Category?.Name
                binding.txtStock.text = it.Stock.toString()
                val uri = ApiConsts.photoApiBaseUrl+it.PhotoPath
                Glide.with(this).load(uri).into(binding.productImage)
            }
        })

        binding.btnDelete.setOnClickListener {
            viewModel.deleteProduct(args.productId).observe(viewLifecycleOwner, Observer {
                if(it){
                    Snackbar.make(requireView(),"Successfully deleted",Snackbar.ANIMATION_MODE_SLIDE).show()
                    val action = ProductDetailFragmentDirections.actionProductDetailFragmentToProductListFragment()
                    findNavController().navigate(action)

                }
            })
        }
        binding.btnUpdate.setOnClickListener {
            val action = ProductDetailFragmentDirections.actionProductDetailFragmentToProductUpdateFragment(productNavigation)
            findNavController().navigate(action)

        }

        return binding.root
    }


}