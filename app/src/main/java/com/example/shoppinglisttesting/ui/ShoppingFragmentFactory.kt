package com.example.shoppinglisttesting.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.example.shoppinglisttesting.adapters.ImageAdapter
import com.example.shoppinglisttesting.adapters.ShoppingItemAdapter
import com.example.shoppinglisttesting.ui.fragments.AddShoppingItemFragment
import com.example.shoppinglisttesting.ui.fragments.ImagePickFragment
import com.example.shoppinglisttesting.ui.fragments.ShoppingFragment
import javax.inject.Inject

class ShoppingFragmentFactory @Inject constructor(
            private val imageAdapter: ImageAdapter,
            private val glide : RequestManager,
            private val itemAdapter : ShoppingItemAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
                ImagePickFragment::class.java.name -> ImagePickFragment(imageAdapter)
                AddShoppingItemFragment::class.java.name -> AddShoppingItemFragment(glide)
                ShoppingFragment::class.java.name -> ShoppingFragment(itemAdapter)
                else -> super.instantiate(classLoader, className)
        }

    }

}