package com.example.shoppinglisttesting.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shoppinglisttesting.adapters.ImageAdapter
import com.example.shoppinglisttesting.databinding.FragmentImagePickBinding
import com.example.shoppinglisttesting.utils.Constants.GRID_SPAN_COUNT
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ImagePickFragment @Inject constructor(
    val imageAdapter: ImageAdapter
) : BaseFragment<FragmentImagePickBinding>(
    FragmentImagePickBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()

        imageAdapter.setOnClickListener {
            findNavController().popBackStack()
            viewModel.setCurrentImageUrl(it)
        }

    }

    private fun setRecyclerView() {

        bind.rvImages.apply {

            adapter = imageAdapter
            layoutManager = GridLayoutManager(requireContext(), GRID_SPAN_COUNT)

        }

    }

}