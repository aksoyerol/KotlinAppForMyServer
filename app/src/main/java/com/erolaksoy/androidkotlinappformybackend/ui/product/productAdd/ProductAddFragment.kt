package com.erolaksoy.androidkotlinappformybackend.ui.product.productAdd

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.erolaksoy.androidkotlinappformybackend.R
import com.erolaksoy.androidkotlinappformybackend.databinding.ProductAddFragmentBinding
import com.erolaksoy.androidkotlinappformybackend.models.Category
import com.erolaksoy.androidkotlinappformybackend.models.Product
import com.erolaksoy.androidkotlinappformybackend.ui.product.productList.ProductListFragmentDirections
import com.erolaksoy.androidkotlinappformybackend.ui.user.UserActivity
import com.google.android.material.tabs.TabLayout

class ProductAddFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().findViewById<TabLayout>(R.id.userTabLayout).visibility = View.GONE
        val binding = ProductAddFragmentBinding.inflate(inflater, container, false)

        val viewModel = ViewModelProvider(this).get(ProductAddViewModel::class.java)
        UserActivity.setLoadingState(viewModel, viewLifecycleOwner)
        UserActivity.setErrorState(viewModel, viewLifecycleOwner)

        viewModel.getCategories().observe(viewLifecycleOwner, Observer {
            val spinnerAdaptor =
                ArrayAdapter<Category>(requireContext(), android.R.layout.simple_spinner_item, it)
            spinnerAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerCategory.setAdapter(spinnerAdaptor)
        })

        binding.textButton.setOnClickListener {
            val selectedCategory = binding.spinnerCategory.getSpinner().selectedItem as Category
            val product = Product(
                //TODO validation
                Id = 0,
                Name = binding.txtName.editText?.text.toString(),
                Color = binding.txtColor.editText?.text.toString(),
                Price = binding.txtPrice.editText?.text.toString().toDouble(),
                Stock = binding.txtStock.editText?.text.toString().toInt(),
                PhotoPath = " ",
                CategoryId = selectedCategory.Id,
            )

            viewModel.addProduct(product, null).observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG)
                        .show()
                    val action =
                        ProductAddFragmentDirections.actionProductAddFragmentToProductListFragment()
                    findNavController().navigate(action)
                }
            })
        }


        binding.lifecycleOwner = this
        return binding.root

    }

}