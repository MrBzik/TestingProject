package com.example.shoppinglisttesting.repositories

import androidx.lifecycle.LiveData
import com.example.shoppinglisttesting.data.local.entities.ShoppingItem
import com.example.shoppinglisttesting.data.remote.responses.PixabayResponse
import com.example.shoppinglisttesting.utils.Resource
import retrofit2.Response

interface ShoppingRepository {


    suspend fun insertShoppingItem (item : ShoppingItem)

    suspend fun deleteShoppingItem (item : ShoppingItem)

    fun observeTotalPrice () : LiveData<Float>

    fun observeAllItems () : LiveData<List<ShoppingItem>>

    suspend fun searchForImages (query : String) : Resource<PixabayResponse>

}