package com.erolaksoy.androidkotlinappformybackend.ui.product.productUpdate

import android.R
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
import com.bumptech.glide.Glide
import com.erolaksoy.androidkotlinappformybackend.consts.ApiConsts
import com.erolaksoy.androidkotlinappformybackend.databinding.ProductUpdateFragmentBinding
import com.erolaksoy.androidkotlinappformybackend.models.Category
import com.erolaksoy.androidkotlinappformybackend.ui.user.UserActivity
import com.google.android.material.snackbar.Snackbar

class ProductUpdateFragment : Fragment() {

    private lateinit var binding: ProductUpdateFragmentBinding
    private var virtualFileUri: Uri? = null

    companion object {
        const val REQUEST_CODE_IMAGE_PICK = 101
        const val REQUEST_CODE_PERMISSION = 200
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProductUpdateFragmentBinding.inflate(inflater, container, false)

        val arguments = ProductUpdateFragmentArgs.fromBundle(requireArguments())
        val viewModel = ViewModelProvider(this).get(ProductUpdateViewModel::class.java)
        UserActivity.setLoadingState(viewModel, viewLifecycleOwner)
        UserActivity.setErrorState(viewModel, viewLifecycleOwner)

        val photoUrl = "${ApiConsts.photoApiBaseUrl}/${arguments.argProduct.PhotoPath}"
        Glide.with(requireContext()).load(photoUrl).override(200, 200).into(binding.updatePhoto)
        arguments.argProduct.also {
            binding.txtColor.editText!!.setText(it.Color)
            binding.txtName.editText!!.setText(it.Name)
            binding.txtPrice.editText!!.setText(it.Price.toString())
            binding.txtStock.editText!!.setText(it.Stock.toString())
        }

        //spinner selected item showing
        viewModel.getCategoryList().observe(viewLifecycleOwner, Observer { it ->
            val spinnerAdapter = ArrayAdapter<Category>(
                requireContext(),
                R.layout.simple_spinner_item, it
            )
            spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
            val selectedPosition = it.map { it.Id }.indexOf(arguments.argProduct.CategoryId)
            binding.spinnerCategory.setAdapter(spinnerAdapter)
            binding.spinnerCategory.getSpinner().setSelection(selectedPosition)
        })

        binding.updatePhoto.setOnClickListener {
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
        binding.btnUpdate.setOnClickListener {
            val selectedSpinnerItem = binding.spinnerCategory.getSpinner().selectedItem as Category
            val updatedProduct = arguments.argProduct.also {
                it.Name = binding.txtName.editText?.text.toString()
                it.Color = binding.txtColor.editText?.text.toString()
                it.Price = binding.txtPrice.editText?.text.toString().toDouble()
                it.Stock = binding.txtStock.editText?.text.toString().toInt()
                it.CategoryId = selectedSpinnerItem.Id
            }

            viewModel.updateProduct(updatedProduct, virtualFileUri)
                .observe(viewLifecycleOwner, Observer {
                    if (it) {
                        Snackbar.make(requireView(),"Successfully edited ${updatedProduct.Name}",Snackbar.LENGTH_LONG).show()
                        val action =
                            ProductUpdateFragmentDirections.actionProductUpdateFragmentToProductListFragment()
                        findNavController().navigate(action)
                    }
                })

        }


        return binding.root
    }

    private fun showGallery() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            startActivityForResult(it, REQUEST_CODE_IMAGE_PICK)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_IMAGE_PICK && resultCode == Activity.RESULT_OK) {
            virtualFileUri = data?.data!!
            binding.updatePhoto.setImageURI(virtualFileUri)
        }
    }

}