package com.erolaksoy.androidkotlinappformybackend.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import com.erolaksoy.androidkotlinappformybackend.databinding.ProductListLoadingItemBinding

class ProductListLoadingHolder(private val binding: ProductListLoadingItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val progressBar = binding.progressBar
}