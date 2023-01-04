package com.example.shoppinglisttesting.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.shoppinglisttesting.R
import com.example.shoppinglisttesting.databinding.FragmentShoppingBinding

class ShoppingFragment : BaseFragment<FragmentShoppingBinding>(
    FragmentShoppingBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind.fabAddShoppingItem.setOnClickListener {

            findNavController().navigate(ShoppingFragmentDirections
                    .actionShoppingFragmentToAddShoppingItemFragment())

        }

    }

}