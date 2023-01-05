package com.example.shoppinglisttesting.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglisttesting.R
import com.example.shoppinglisttesting.adapters.ShoppingItemAdapter
import com.example.shoppinglisttesting.databinding.FragmentShoppingBinding
import com.example.shoppinglisttesting.ui.ShoppingViewModel
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class ShoppingFragment @Inject constructor(
        private val itemAdapter : ShoppingItemAdapter,
        private val shoppingViewModel : ShoppingViewModel? = null
): BaseFragment<FragmentShoppingBinding>(
    FragmentShoppingBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shoppingViewModel?.let {
            viewModel = it
        }

        subscribeToObservers()
        setRecyclerView()

        bind.fabAddShoppingItem.setOnClickListener {

            findNavController().navigate(ShoppingFragmentDirections
                    .actionShoppingFragmentToAddShoppingItemFragment())
        }
    }


    private val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(
        0,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.layoutPosition
            val item = itemAdapter.shoppingItems[position]

           viewModel.deleteShoppingItem(item).also {
                Snackbar.make(requireView(), "item deleted", Snackbar.LENGTH_LONG).apply {
                    setAction("UNDO"){
                        viewModel.insertShoppingItemIntoDB(item)
                    }
                }.show()
            }
        }
    }

    private fun setRecyclerView(){

        bind.rvShoppingItems.apply {

            adapter = itemAdapter
            layoutManager = LinearLayoutManager(requireContext())
            ItemTouchHelper(itemTouchCallback).attachToRecyclerView(this)
        }
    }

    private fun subscribeToObservers(){

        viewModel.observeTotalPrice.observe(viewLifecycleOwner){

            val price = it ?: 0f

            val priceText = "Total price : $price"

            bind.tvShoppingItemPrice.text = priceText


        }

        viewModel.observeAllItems.observe(viewLifecycleOwner){
            itemAdapter.shoppingItems = it
        }

    }

}