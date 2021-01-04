package com.erolaksoy.androidkotlinappformybackend.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.erolaksoy.androidkotlinappformybackend.R
import com.erolaksoy.androidkotlinappformybackend.consts.ApiConsts
import com.erolaksoy.androidkotlinappformybackend.databinding.ProductListItemBinding
import com.erolaksoy.androidkotlinappformybackend.models.Product

class ProductListItemHolder(private val binding: ProductListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    private val txtProductName = binding.productName
    private val txtProductStock = binding.productStock
    private val txtProductPrice = binding.productPrice
    private val productImage = binding.productImage

    fun bind(item: Product) {
        txtProductName.text = item.Name
        txtProductStock.text = item.Stock.toString()
        txtProductPrice.text = item.Price.toString()
        val imageUri = if(item.PhotoPath!=" " && item.PhotoPath!=null){"${ApiConsts.photoApiBaseUrl}/${item.PhotoPath}"}else null

            Glide.with(productImage.context).load(imageUri)
                .apply(
                    RequestOptions().placeholder(R.drawable.add_photo).error(R.drawable.login_image)
                ).into(productImage)


    }
}