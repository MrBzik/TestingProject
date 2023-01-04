package com.example.shoppinglisttesting.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.example.shoppinglisttesting.databinding.FragmentAddShoppingItemBinding

class AddShoppingItemFragment : BaseFragment<FragmentAddShoppingItemBinding>(
            FragmentAddShoppingItemBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind.ivShoppingImage.setOnClickListener {

            findNavController().navigate(AddShoppingItemFragmentDirections
                .actionAddShoppingItemFragmentToImagePickFragment())

        }

        val onBackPressedCallback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                viewModel.setCurrentImageUrl("")
                findNavController().popBackStack()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)

    }



}