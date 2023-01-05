package com.example.shoppinglisttesting.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.shoppinglisttesting.ui.ShoppingFragmentFactory
import com.example.shoppinglisttesting.ui.ShoppingViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

abstract class BaseFragment <VB : ViewBinding>  (
            val bindInflater : (inflater : LayoutInflater) -> VB

        ): Fragment() {

    lateinit var viewModel : ShoppingViewModel


    private var _binding : VB? = null

    val bind : VB
    get() = _binding as VB


    override fun onCreateView(


        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

                _binding = bindInflater(inflater)


        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[ShoppingViewModel::class.java]
    }

}