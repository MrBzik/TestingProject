package com.example.shoppinglisttesting.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglisttesting.data.local.entities.ShoppingItem
import com.example.shoppinglisttesting.data.remote.responses.PixabayResponse
import com.example.shoppinglisttesting.utils.Resource

class FakeMainRepository : ShoppingRepository {

   private val shoppingItems = mutableListOf<ShoppingItem>()

    private val observableShoppingItems = MutableLiveData<List<ShoppingItem>>(shoppingItems)

    private val observableTotalPrice = MutableLiveData<Float>()

    private var isErrorNeeded = false

    fun setErrorIsNeeded(value : Boolean) {

        isErrorNeeded = value
    }

    override suspend fun insertShoppingItem(item: ShoppingItem) {
        shoppingItems.add(item)
        observableShoppingItems.postValue(shoppingItems)
    }

    override suspend fun deleteShoppingItem(item: ShoppingItem) {
        shoppingItems.remove(item)
        observableShoppingItems.postValue(shoppingItems)
    }

    override fun observeTotalPrice(): LiveData<Float> {
        var totalPrice = 0f
        shoppingItems.forEach {
            totalPrice += (it.price *it.amount )
        }
        observableTotalPrice.postValue(totalPrice)

        return observableTotalPrice
    }

    override fun observeAllItems(): LiveData<List<ShoppingItem>> {

        return observableShoppingItems

    }

    override suspend fun searchForImages(query: String): Resource<PixabayResponse> {

        if(isErrorNeeded) {

            return Resource.error("some error", null)

        } else {
            return Resource.success(PixabayResponse(listOf(), 0, 0))
        }

    }
}