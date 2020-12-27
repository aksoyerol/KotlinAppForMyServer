package com.erolaksoy.androidkotlinappformybackend.ui.product.productAdd

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import com.erolaksoy.androidkotlinappformybackend.R
import com.erolaksoy.androidkotlinappformybackend.databinding.ProductAddFragmentBinding
import com.erolaksoy.androidkotlinappformybackend.models.Category
import com.erolaksoy.androidkotlinappformybackend.models.Product
import com.google.android.material.tabs.TabLayout

class ProductAddFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().findViewById<TabLayout>(R.id.userTabLayout).visibility = View.GONE
        val binding = ProductAddFragmentBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProvider(this).get(ProductAddViewModel::class.java)
        viewModel.getCategories().observe(viewLifecycleOwner, Observer {
            val spinnerAdaptor = ArrayAdapter<Category>(requireContext(),android.R.layout.simple_spinner_item,it)
            spinnerAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerCategory.setAdapter(spinnerAdaptor)
        })

        binding.spinnerCategory.setOnClickListener {
            val selectedCategory = binding.spinnerCategory.getSpinner().selectedItem as Category
//            val product = Product(
//                //TODO validation
//                Name = binding.txtName.editText?.text.toString(),
//                Category = selectedCategory,
//                Color = binding.txtColor.editText?.text.toString(),
//                Price = binding.txtPrice.editText?.text.toString().toDouble(),
//                Stock = binding.txtStock.editText?.text.toString().toInt(),
//
//            )
        }

        return binding.root

    }

}