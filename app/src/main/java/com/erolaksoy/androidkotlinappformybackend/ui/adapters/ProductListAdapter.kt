package com.erolaksoy.androidkotlinappformybackend.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.erolaksoy.androidkotlinappformybackend.databinding.ProductListItemBinding
import com.erolaksoy.androidkotlinappformybackend.databinding.ProductListLoadingItemBinding
import com.erolaksoy.androidkotlinappformybackend.models.Product

class ProductListAdapter(val productList: ArrayList<Product>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_LOADING = 0
        private const val VIEW_TYPE_ITEM = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (productList[position].Id == 0) {
            VIEW_TYPE_LOADING
        } else {
            VIEW_TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == VIEW_TYPE_LOADING) {
            ProductListLoadingHolder(ProductListLoadingItemBinding.inflate(inflater, parent, false))
        } else {
            ProductListItemHolder(ProductListItemBinding.inflate(inflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = productList[position]
        if (holder is ProductListItemHolder) {
            holder.bind(item)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    fun addProduct(newProducts: ArrayList<Product>) {
        productList.addAll(newProducts)
        notifyDataSetChanged()
    }

    fun addLoading() {
        val loadingProduct = Product(0, "", 0.0, "", 0, "", 0)
        productList.add(loadingProduct)
        notifyDataSetChanged()
    }

    fun removeLoading() {
        val position = productList.size - 1
        productList.removeAt(position)
        notifyDataSetChanged()
    }
}