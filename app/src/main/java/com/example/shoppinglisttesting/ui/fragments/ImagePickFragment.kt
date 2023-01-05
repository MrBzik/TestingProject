package com.example.shoppinglisttesting.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shoppinglisttesting.R
import com.example.shoppinglisttesting.adapters.ImageAdapter
import com.example.shoppinglisttesting.databinding.FragmentImagePickBinding
import com.example.shoppinglisttesting.utils.Constants.GRID_SPAN_COUNT
import com.example.shoppinglisttesting.utils.Event
import com.example.shoppinglisttesting.utils.Resource
import com.example.shoppinglisttesting.utils.Status
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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

            bind.etSearch.text?.let { query ->
                viewModel.updateSearchQuery(query.toString())
            }
            findNavController().popBackStack()
            viewModel.setCurrentImageUrl(it)
        }

        var job: Job? = null

        bind.etSearch.addTextChangedListener {

            job?.cancel()

            job = lifecycleScope.launch {

                delay(1200)

                viewModel.searchForImages(it.toString())

            }



        }

        viewModel.images.observe(viewLifecycleOwner){

            it.getContentIfNotHandled()?.let { response ->
                when(response.status) {

                    Status.LOADING -> bind.progressBar.visibility = View.VISIBLE

                    Status.SUCCESS -> {

                        val urls = response.data?.hits?.map { hit ->
                            hit.previewURL
                        }
                        imageAdapter.images = urls ?: listOf()

                        bind.progressBar.visibility = View.GONE
                    }

                    Status.ERROR -> {
                        bind.progressBar.visibility = View.GONE
                        Snackbar.make(requireActivity().findViewById(R.id.rootLayout),
                                response.message ?: "some error", Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }


    }

    private fun setRecyclerView() {

        bind.rvImages.apply {

            adapter = imageAdapter
            layoutManager = GridLayoutManager(requireContext(), GRID_SPAN_COUNT)

        }

    }

}