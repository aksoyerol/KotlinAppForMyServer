package com.erolaksoy.androidkotlinappformybackend.ui.product.productAdd

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.erolaksoy.androidkotlinappformybackend.R
import com.erolaksoy.androidkotlinappformybackend.databinding.ProductAddFragmentBinding
import com.erolaksoy.androidkotlinappformybackend.models.Category
import com.erolaksoy.androidkotlinappformybackend.models.Product
import com.erolaksoy.androidkotlinappformybackend.ui.user.UserActivity
import com.google.android.material.tabs.TabLayout

class ProductAddFragment : Fragment() {

    companion object {
        const val REQUEST_CODE_IMAGE_PICK = 101
        const val REQUEST_CODE_PERMISSION = 200
    }

    private lateinit var binding: ProductAddFragmentBinding
    private var virtualFileUri: Uri? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().findViewById<TabLayout>(R.id.userTabLayout).visibility = View.GONE
        binding = ProductAddFragmentBinding.inflate(inflater, container, false)

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
            //TODO validation
            val product = Product(
                Id = 0,
                Name = binding.txtName.editText?.text.toString(),
                Color = binding.txtColor.editText?.text.toString(),
                Price = binding.txtPrice.editText?.text.toString().toDouble(),
                Stock = binding.txtStock.editText?.text.toString().toInt(),
                PhotoPath = virtualFileUri.toString(),
                CategoryId = selectedCategory.Id,
                Category = null
            )

            viewModel.addProduct(product, virtualFileUri).observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG)
                        .show()
                    val action =
                        ProductAddFragmentDirections.actionProductAddFragmentToProductListFragment()
                    findNavController().navigate(action)
                }
            })
        }
        binding.addPhoto.setOnClickListener {
            //izin kontrolü
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_CODE_PERMISSION
                )
            } else {
                showGallery()
            }
        }
        return binding.root
    }

    private fun showGallery() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            startActivityForResult(it, REQUEST_CODE_IMAGE_PICK)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_IMAGE_PICK && resultCode == Activity.RESULT_OK) {
            virtualFileUri = data!!.data
            binding.addPhoto.setImageURI(virtualFileUri)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) showGallery()
            }
        }
    }


}