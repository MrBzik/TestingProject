package com.example.shoppinglisttesting.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.example.shoppinglisttesting.R
import com.example.shoppinglisttesting.databinding.FragmentAddShoppingItemBinding
import com.example.shoppinglisttesting.utils.Status
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class AddShoppingItemFragment @Inject constructor(
    val glide : RequestManager
) : BaseFragment<FragmentAddShoppingItemBinding>(
    FragmentAddShoppingItemBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToObservers()

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


        bind.btnAddShoppingItem.setOnClickListener {

            val name = bind.etShoppingItemName.text.toString()
            val amount = bind.etShoppingItemAmount.text.toString()
            val price = bind.etShoppingItemPrice.text.toString()

            viewModel.insertShoppingItem(name, amount, price)
        }

    }

    fun subscribeToObservers(){
        viewModel.currentImageUrl.observe(viewLifecycleOwner){

            glide.load(it).into(bind.ivShoppingImage)

        }

        viewModel.insertShoppingItemStatus.observe(viewLifecycleOwner){

            it.getContentIfNotHandled()?.let { result ->
                when (result.status){
                    Status.ERROR -> {
                        Snackbar.make(requireActivity().findViewById(R.id.rootLayout),
                            result.message ?: "mysterious fail", Snackbar.LENGTH_SHORT).show()
                    }
                    Status.SUCCESS -> {
                        Snackbar.make(requireActivity().findViewById(R.id.rootLayout),
                            "item added", Snackbar.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                    }
                    else -> {/* NO-OP */}
                }
            }
        }
    }

}